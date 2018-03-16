
### Why I need a blank application?

To test in a vacuum the code changes needed to migrate from GCM to -> FCM

In the workplace we need to this very same thing but since it's live 
we need to get it right in a single release.

##### Send a push notification from the console 

```
curl -X POST \
-H "Authorization: key= YOUR-API-KEY" \
-H "Content-Type: application/json" \
-d '{ 
"registration_ids": [ 
"YOUR-GCM-REGISTRATION-ID"
], 
"data": { 
"message": "Hello Message"
},
"priority": "high"
}' \
https://android.googleapis.com/gcm/send

```