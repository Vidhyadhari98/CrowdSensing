import time
import json
import requests
import paho.mqtt.client as mqtt

broker = "test.mosquitto.org"

coachData = requests.get(url="https://iot-project-20240110.ew.r.appspot.com/coach").json()
print(json.dumps(coachData, indent=2))


trains = dict()
coaches = dict()

for coach in coachData:
    print(coach)

    trainId = coach['train_id']
    if trainId not in trains:
        trains[trainId] = {
            'id': trainId,
            'coaches': []
        }

    trains[trainId]['coaches'].append(coach['id'])

    coachId = coach['id']
    coaches[coachId] = {
        'id': coachId,
        'position': coach['position'],
        'train_id': trainId,
        'crowdedness': 0.0
    }

def getTrain(id):
    train = trains[id].copy()
    coachesId = train['coaches']

    train['coaches'] = []

    for i in coachesId:
        train['coaches'].append(coaches[i])

    return train

def updateCoach(id, crowdedness):
    coaches[int(id)]['crowdedness'] = crowdedness


#creating a mqtt client instance
client = mqtt.Client()

#connecting to the mqtt broker
client.connect(broker)
client.loop_start()

def on_connect(client, uderdata, flags, rc):
    print("Connected")

client.on_connect = on_connect

def on_message(client, userdata, message):
    data = str(message.payload.decode("utf-8"))
    print("Got"+ data)
    json_object= json.loads(data)
    coachId = json_object['id']
    crowdedness = json_object['occupiedSeats'] / json_object['totalSeats']
    print("calculated crowdedness " +str(crowdedness))
    updateCoach(coachId, crowdedness)

client.on_message = on_message

#subscribing to the topic
for id in coaches:
    print("Subscribing to: " + "0207/coach/"+str(id))
    client.subscribe("0207/coach/"+str(id), qos = 0)


#publsihing the information
while True:
    #Converting to json
    for id in trains:
        data = getTrain(id)
        print("train id" + str(data))
        json_data = json.dumps(data)
        print("Publish 0207/train/"+str(id)+": " + str(data))
        client.publish('0207/train/'+str(id), json_data, qos = 0)
    time.sleep(3)