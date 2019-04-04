# WebUntisService

Java SE Application that periodically checks the webuntis api for updates and manages the epapers via mqtt

## Cases when the service sends updates over mqtt

 * break begins
 * update timestamp of webuntis changes
 * new lesson begins
    
## Different ways we can do initial synchronization

### Mqtt

We could use a custom topic something like "init" with a payload of "{ room: 'E72' }".

The new esp would send a mqtt message with a topic of "init" and a payload of '{  "room": "E72" }'
