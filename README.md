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
