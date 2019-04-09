# WebUntisService

Java SE Application that periodically checks the webuntis api for updates and manages the epapers via mqtt

## Cases when the service sends updates over mqtt

 * break begins
 * update timestamp of webuntis changes
 * new lesson begins
    
## Different ways we can do initial synchronization

### Mqtt

The server is subscribed to the init topic.

The new esp would send a mqtt message with a topic of "init" and a payload of 'E72'

The new esp would subscribe to 'init/E72'

The server sends a mqtt message with a topic of "init/E72" and a payload of '{ ...roomState }'

the new esp unsubscribes from 'init/E72'

#### Problems

This way seems really weird. Subscribing to a topic for only one message and then unsubscribing just doesn't feel right.

Since we don't know who sent the answer we don't know whether someone is messing with us. We might have to add some confirmation token or something like that.

#### Good things

We don't know that the server exists.

### Http

The server listens on the /init route.

The new esp sends a get request to the server at the /init route with the query parameter room=E72.

The server responds with the state of the room as json.

{ ...roomState }

#### Problems

The esp has to know about the server which is sending out the mqtt messages.

#### Good things

We can be pretty sure that the response isn't some funny guy messing with us.


