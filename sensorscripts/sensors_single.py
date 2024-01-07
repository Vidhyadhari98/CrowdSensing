import time
import json
import board
import adafruit_vcnl4010
import paho.mqtt.client as mqtt
import sys
import busio

############## Commandline argument (coach id) section ##############

if len(sys.argv) <=1:
    print("Please provide a coach id as the first argument when running this script. \nExample: 'Scriptname.py, ID'")
    exit()

if len(sys.argv) >2:
    print("Too many arguments provided, use just one argument. \nExample: 'Scriptname.py, ID'")
    exit()

else:
    print("Script name: ", sys.argv[0])
    for i in range(1, len(sys.argv)):                                       # Parses argv string from sys.argv[1]
        print('Argument:', i, 'value:', sys.argv[i])
        id = sys.argv[i]                                                    # Assigns the last argument found to id (but we make sure we only get two arguments so the index i is fixed as "$


id=sys.argv[1]                                                              # sys.argv[0] contains filename, sys.argv[1] is the id passed along
                                              # Sets the variable id to the first argument passed along from the commandline  to the script

############## hw init section ##############

broker = "test.mosquitto.org"                                              # Test Broker.
topic = "coach/"+str(id)
i2c = busio.I2C(board.SCL, board.SDA)                                                           # Init board
sensor_prox = adafruit_vcnl4010.VCNL4010(i2c)

############### Sensor section ##################
def get_proximity(sensor_prox):
        proximity = sensor_prox.proximity
        return proximity

############### MQTT section ################## (from lab)

# when connecting to mqtt do this
def on_connect(client, userdata, flags, rc):
        if rc==0:
                print("Connection established. Code: "+str(rc))
        else:
                print("Connection failed. Code: " + str(rc))

def on_publish(client, userdata, mid):
    print("Published: " + str(mid))

def on_disconnect(client, userdata, rc):
        if rc != 0:
                print ("Unexpected disonnection. Code: ", str(rc))
        else:
                print("Disconnected. Code: " + str(rc))

def on_log(client, userdata, level, buf):                                           # Message is in buf
    print("MQTT Log: " + str(buf))

# Connect functions for MQTT
client = mqtt.Client()
client.on_connect = on_connect
client.on_disconnect = on_disconnect
client.on_publish = on_publish
client.on_log = on_log

# Connect to MQTT
print("Attempting to connect to broker " + broker)
client.connect(broker)                                                      # Broker address, port and keepalive (maximum period in seconds allowed between communications with the broker)
client.loop_start()

########### Data processing and publishing #############
while True:
  total_seat = 1
  prox_val = get_proximity(sensor_prox)
  if prox_val <=2600:
      occupied_seat= 0
  else:
      occupied_seat=1
  available_seat = total_seat - occupied_seat
  coach_status = {                                                     # Create a dict to contain values
        "id": id,
        "occupiedSeats": occupied_seat,
        "availableSeats": available_seat,
        "totalSeats": total_seat
    }
  coach_status_json = json.dumps(coach_status)                             # Convert dict to json string
  payload=coach_status_json
  client.publish(topic, str(payload), qos=0)                              # Publish
  time.sleep(1.0)
  client.publish(topic, str(payload))
  time.sleep(2.0)       # Set delay

