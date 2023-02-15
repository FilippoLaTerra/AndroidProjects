package com.lock.newtemiactionsystemtest.helpers;

public class JsonHelper {

    private String json;

    private static JsonHelper instance = null;

    private JsonHelper(){
        this.json = "";
    }

    public static JsonHelper getInstance() {
        if (instance == null)
            instance = new JsonHelper();

        return instance;
    }
}


//try using jackson for json parsing

/*

{ "Task1": {  "task_id": 1000000000,
              "taskName": "delivery",
              "programmed" : null,
              "actions" : [
                          { "action_id": 101,
                            "actionName": "moveTo",
                            "location": "locationName",
                            "speed": null,
                            "backwards":false
                          },
                          { "action_id": 102,
                          "actionName": "controlLock",
                          "state": "open",
                          "needsInput": false,
                          "code": null
                          }
                          ]
            }
}



 */