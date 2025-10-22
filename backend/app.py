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
    "id":str(uuid.uuid3(uuid.NAMESPACE_DNS, "Dummy"))
}}
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

@app.route('/users', methods=[ 'POST'])
def handle_register():
    if request.method == 'POST':
        try:
            data = request.get_json()
        except:
            return jsonify({
                "status": "error",
                "data": "Invalid JSON or missing data in request body."
            }), 400  # Return 400 Bad Request

        if data and 'username' in data and 'email' in data and 'password' in data:
            username = data['username']
            email = data['email']
            password = data['password']
            users[email] = {"username":username, "password":password,"id": str(uuid.uuid3(uuid.NAMESPACE_DNS, username))}

            # Return a success message with the received data
            return jsonify({
                "status":"success",
                "data": {
                "username":username,
                "email":email,
                "id":users[email]["id"]
            }
            }),200
        else:
            return jsonify({
                "status": "error",
                "data": "POST request successful, but 'user' or 'value' fields are missing."
            }), 400

@app.route('/auth/login', methods=['POST'])
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
        print(user)
        if users[email]["password"] == password: 
            # ⭐️ Successful Login ⭐️
            return jsonify({
                "status": "success",
                "data": {
                    "username":users[email]['username'],
                    "email":email,
                    "id":users[email]["id"]
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


@app.route('/posts', methods=['GET',"POST"])
def handle_post():
    if request.method == 'POST':
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

@app.route('/posts/<string:postId>/comments', methods=['GET','POST'])
def handle_comment(postId):
    if request.method == 'POST':
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
        userid = data['userid']
        username = data['username']
        postId = postId
        id = str(uuid.uuid3(uuid.NAMESPACE_DNS,userid+"comment"+text))
        return jsonify({
            "status":"success",
            data:{"username":username,
            "userid":userid,
            "text":text,
            "id":id}                
            })


    else:
        return jsonify({
            "status":"success",
            "data":comments
        })



if __name__ == '__main__':
    app.run(debug=True)