import requests
import json
import stomp

QUEUE_NAME = 'MESSAGES.T.ALL'

def determineBrokerUrls():
    # TODO: use environment variables and the like here
    return ['mq-broker-0.docker.internal',
            'mq-broker-1.docker.internal']

def fetchQueueDepth(queue):
    queueMetricUrl = '/api/jolokia/read'
    queueRequestBody = '{"type": "read","mbean":"org.apache.activemq:brokerName=localhost,destinationName=' + queue + ',destinationType=Queue,type=Broker","attribute":"QueueSize"}'

    urls = determineBrokerUrls()

    for url in urls:
        fullUrl = 'http://' + url + queueMetricUrl
        print(fullUrl)
        res = requests.post(fullUrl, auth=('admin', 'admin'), data=queueRequestBody)

        print(res)

        if res.status_code == 200:
            data = json.loads(res.text)
            print(data)

            if 'value' in data:
                return data['value']

    return -1

def publishMessage(queue, message, count):
    servers = [(x, 61613) for x in determineBrokerUrls()]

    con = stomp.Connection(servers)
    con.start()
    con.connect('admin', 'admin', wait=True)

    for i in range(0, count):
        con.send(body = message, destination = queue)

    con.disconnect()
    return True

if __name__ == '__main__':
    print(fetchQueueDepth(QUEUE_NAME))
#    publishMessage('MESSAGES.T.ALL', 'asdf', 3)
