#!/usr/bin/env python3

import stomp

conn = stomp.Connection()
conn.start()
conn.connect('admin', 'admin', wait=True)
conn.send(body = 'asdfasdf', destination = 'MESSAGES.T.ALL')

