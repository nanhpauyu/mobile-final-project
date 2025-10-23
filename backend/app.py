from flask import Flask, request, jsonify
import uuid
import logging
logging.basicConfig(
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
    level=logging.INFO
)
logger = logging.getLogger(__name__)

app = Flask(__name__)
users = {"dummy@dummy.com":{
    "username":"Dummy",
    "email": "dummy@dummy.com",
    "password":"password",
    "id":str(uuid.uuid3(uuid.NAMESPACE_DNS, "Dummy")),
    "bio":"This is my bio"
}}
id_to_user_email = {
    str(uuid.uuid3(uuid.NAMESPACE_DNS, "Dummy")) :"dummy@dummy.com"
}
user_session ={} # {uuid: email}

user_uuid = str(uuid.uuid3(uuid.NAMESPACE_DNS, "Dummy"))
posts = {
    str(uuid.uuid3(uuid.NAMESPACE_DNS, user_uuid + "This is my first post")): {
        "userId": user_uuid,
        "username": "Dummy",
        "text": "This is my first post",
        "id": str(uuid.uuid3(uuid.NAMESPACE_DNS, user_uuid + "This is my first post"))
    },
    str(uuid.uuid3(uuid.NAMESPACE_DNS, user_uuid + "This is my second post")): {
        "userId": user_uuid,
        "username": "Dummy", 
        "text": "This is my second post",
        "id": str(uuid.uuid3(uuid.NAMESPACE_DNS, user_uuid + "This is my second post"))
    },
}
comments = {
    str(uuid.uuid3(uuid.NAMESPACE_DNS,str(uuid.uuid3(uuid.NAMESPACE_DNS, "Dummy"))+"This is my second post")) :{
        "comments":[
            {
                "userId":str(uuid.uuid3(uuid.NAMESPACE_DNS, "Dummy")),
                "username":"Dummy",
                "text":"This is comment",
                "id": str(uuid.uuid3(uuid.NAMESPACE_DNS,str(uuid.uuid3(uuid.NAMESPACE_DNS, "Dummy"))+"comment"+"This is comment"))
            },
            {
                "userId":str(uuid.uuid3(uuid.NAMESPACE_DNS, "Dummy")),
                "username":"Dummy",
                "text":"This is second comment",
                "id": str(uuid.uuid3(uuid.NAMESPACE_DNS,str(uuid.uuid3(uuid.NAMESPACE_DNS, "Dummy"))+"comment"+"This is second comment"))
            }
        ]
    }
}
random_uuid = uuid.uuid4()

@app.route('/register', methods=[ 'POST'])
def handle_register():
    if request.method == 'POST':
        try:
            data = request.get_json()
        except:
            return jsonify({
                "status": "error",
                "data": "Invalid JSON or missing data in request body."
            }), 400  # Return 400 Bad Request
        required_fields = ['email', 'password','username','bio']
        if all(field in data for field in required_fields):
            username = data['username']
            email = data['email']
            password = data['password']
            bio = data['bio']
            id = str(uuid.uuid3(uuid.NAMESPACE_DNS, username))
            users[email] = {"username":username, "password":password,"id": id, "bio":bio,"email":email}
            id_to_user_email[id] = email
            user_session_id = str(uuid.uuid4())
            user_session[user_session_id] = email
            return jsonify({
                "status":"success",
                "data": {
                "username":username,
                "email":email,
                "id":users[email]["id"],
                "bio": bio,
                "access_token": user_session_id
            }
            }),200
        else:
            return jsonify({
                "status": "error",
                "data": "POST request successful, but 'user' or 'value' fields are missing."
            }), 400

@app.route('/login', methods=['POST'])
def handle_login():
    try:
        data = request.get_json()
        if data is None:
            return jsonify({
                "status": "error",
                "data": "Missing JSON data in request body."
            }), 400 
    except Exception:
        return jsonify({
            "status": "error",
            "data": "Invalid JSON format in request body."
        }), 400 

    required_fields = ['email', 'password']
    if not all(field in data for field in required_fields):
        return jsonify({
            "status": "error",
            "data": "Missing required fields: 'email' and 'password'."
        }), 400
    
    # Extract data
    email = data['email']
    password = data['password']
    print(users.keys())
    if email in users.keys():
        user = users[email]
        if users[email]["password"] == password: 
            # ⭐️ Successful Login ⭐️
            user_session_id = str(uuid.uuid4())
            user_session[user_session_id] = email
            return jsonify({
                "status": "success",
                "data": {
                    "username":users[email]['username'],
                    "email":email,
                    "id":users[email]["id"],
                    "bio":users[email]['bio'],
                    "access_token":user_session_id
                },
                
            }), 200 # HTTP 200 OK
        else:
            return jsonify({
                "status": "error",
                "data": "Incorrect password."
            }), 401 # HTTP 401 Unauthorized
    else:
        # Email not found
        return jsonify({
            "status": "error",
            "data": "User with this email not found."
        }), 401 # HTTP 401 Unauthorized


@app.route('/logout',methods=['GET'] )
def handle_logout():
    token = get_bearer_token()
    if token and token in user_session.keys():
        del user_session[token]
        return jsonify({'status': 'success'}), 200
    return jsonify({'status':"fail"})
# logout delete id from the user_session /logout access token from header
# status :success

@app.route('/update_profile', methods=["POST"])
def handle_update_profile():
    token = get_bearer_token()
    if token and token in user_session.keys():
        email = user_session[token]
        user = users[email]
        try:
            data = request.get_json()
            if data is None:
                return jsonify({
                    "status": "error",
                    "data": "Missing JSON data in request body."
                }), 400 
        except Exception:
            return jsonify({
                "status": "error",
                "data": "Invalid JSON format in request body."
            }), 400
        id
        update_username = data['username']
        update_bio = data['bio']
        new_user = {
            "username":update_username,"password":user['password'],'id':user['id'],'bio':update_bio,'email':user['email']
        }
        users[user['email']] = new_user
        return jsonify({
            "status":"success",
            "data":new_user
        })
    else:
        return jsonify({'status':"fail", "data":"Token missmatch"})
        


@app.route('/users/<string:userId>', methods=['GET'])
def handle_users(userId):
    email = id_to_user_email[userId]
    user = users[email]    
    return jsonify({
        "status":"success",
        "data":user
    })


@app.route('/posts', methods=['GET',"POST"])
def handle_post():
    if request.method == 'POST':
        token = get_bearer_token()
        if token and token in user_session.keys():
            try:
                data = request.get_json()
                if data is None:
                    return jsonify({
                        "status": "error",
                        "data": "Missing JSON data in request body."
                    }), 400 
            except Exception:
                return jsonify({
                    "status": "error",
                    "data": "Invalid JSON format in request body."
                }), 400 

            required_fields = ['text', 'userid',"username"]
            if not all(field in data for field in required_fields):
                return jsonify({
                    "status": "error",
                    "data": "Missing required fields: 'email' and 'password'."
                }), 400
            
            # Extract data
            text = data['text']
            userid = data['userid']
            username = data['username']
            id = str(uuid.uuid3(uuid.NAMESPACE_DNS,userid+text))
            posts[id] = {
                "id" :id,
                "userid":userid,
                "username":username,
                "text":text
            }
            return jsonify({
                "status": "success",
                "data": {
                    "username":username,
                    "userid":userid,
                    "text":text,
                    "id":id
                }
            }),200
    else:
        return jsonify({
            "status":"success",
            "data":list(posts.values())
        })




@app.route('/posts/<string:postId>/comments', methods=['GET'])
def handle_comment(postId):
    if request.method == 'POST':
        token = get_bearer_token()
        if token and token in user_session.keys():
            try:
                data = request.get_json()
                if data is None:
                    return jsonify({
                        "status": "error",
                        "data": "Missing JSON data in request body."
                    }), 400 
            except Exception:
                return jsonify({
                    "status": "error",
                    "data": "Invalid JSON format in request body."
                }), 400 
            text = data['text']
            userId = data['userId']
            username = data['username']
            postId = postId
            id = str(uuid.uuid3(uuid.NAMESPACE_DNS,userId+"comment"+text))
            comments[postId]['comments'].append({
                "userId":userId,
                "username":username,
                'text':text,
                'id':id
            })
            return jsonify({
                "status":"success",
                "data":{"username":username,
                "userId":userId,
                "text":text,
                "id":id}                
                }),200


    else:
        return jsonify({
            "status":"success",
            "data":comments[postId]['comments']
        })


def get_bearer_token():
    auth_header = request.headers.get('Authorization')
    
    if auth_header and auth_header.startswith('Bearer '):
        return auth_header.split(' ')[1]
    
    return None


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)