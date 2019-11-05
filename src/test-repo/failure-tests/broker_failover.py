import requests
import json
import stomp

def determineBrokerUrls():
    return ['localhost:8161', 
            'localhost:8162']

def determineBrokerStompUrls():
    return [('localhost', 61613),
            ('localhost', 61614)]

def fetchQueueDepth(queue):
    queueMetricUrl = '/api/jolokia/read'
    queueRequestBody = '{"type": "read","mbean":"org.apache.activemq:brokerName=localhost,destinationName=' + queue + ',destinationType=Queue,type=Broker","attribute":"QueueSize"}'

    urls = determineBrokerUrls()

    for url in urls:
        fullUrl = 'http://' + url + queueMetricUrl
        print('Trying ' + fullUrl)

        try:
            res = requests.post(fullUrl, auth=('admin', 'admin'), data=queueRequestBody)

            print(res)

            if res.status_code == 200:
                data = json.loads(res.text)
                print(data)

                if 'value' in data:
                    return data['value']
        except:
            print("Unable to access " + fullUrl)

    return -1

def publishMessage(queue, message, count):
    servers = determineBrokerStompUrls()

    for server in servers:
        print("Trying server " + str(server))
        try:
            con = stomp.Connection([server])
            con.start()
            con.connect('admin', 'admin', wait=True)

            for i in range(0, count):
                con.send(body = message, destination = queue)

            con.disconnect()
            return True
        except stomp.exception.ConnectFailedException:
            print("Unable to connect to " + str(server))
    return False

if __name__ == '__main__':
    QUEUE_NAME = 'MESSAGES.T.ALL'

#    print(fetchQueueDepth(QUEUE_NAME))
    publishMessage('MESSAGES.T.ALL', 'asdf', 3)
