package com.lock.newtemiactionsystemtest.helpers;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.lock.newtemiactionsystemtest.actions.Action;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.lock.newtemiactionsystemtest.actions.ControlLockerAction;
import com.lock.newtemiactionsystemtest.actions.MoveToLocationAction;
import com.lock.newtemiactionsystemtest.actions.SpeakMessageAction;
import com.lock.newtemiactionsystemtest.tasks.Task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class JsonHelper {

    private String json;

    //WTF IS THIS A SINGLETON??!?!?
    public JsonHelper(){
        String JSON = "{ \"tasks\": [{  \"task_id\": 1000000000,\n" +
                "              \"taskName\": \"delivery\",\n" +
                "              \"programmedDate\" : null,\n" +
                "              \"actions\" : [\n" +
                "                          { \"action_id\": 102,\n" +
                "                            \"actionName\": \"moveTo\",\n" +
                "                            \"location\": \"locationName\",\n" +
                "                            \"speed\": null,\n" +
                "                            \"backwards\":false\n" +
                "                          },\n" +
                "                          { \"action_id\": 103,\n" +
                "                            \"actionName\": \"speakMessage\",\n" +
                "                            \"message\": \"pirupiru\",\n" +
                "                            \"language\": \"IT\"\n" +
                "                          },\n" +
                "                          { \"action_id\": 104,\n" +
                "                            \"actionName\": \"controlLock\",\n" +
                "                            \"state\": \"open\",\n" +
                "                            \"needsInput\": false,\n" +
                "                            \"code\": null\n" +
                "                          }\n" +
                "                          ]\n" +
                "            }]\n" +
                "}";
        json = JSON.replace("\\\"", "\"");
    }

    public ArrayList<Action> parseJsonActions(JSONArray JsonActionArray) throws JSONException, IOException {

        ArrayList<Action> actions = new ArrayList<>();

        for(int i = 0; i < JsonActionArray.length(); i++){

            JSONObject json_action = JsonActionArray.getJSONObject(i);
            int json_action_id = json_action.getInt("action_id");
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
            JsonNode jsonNode = convertJsonFormat(json_action);

            Log.i("ACTION_PARSE", "action_id: " + json_action_id);

            Action parsedAction;
            switch (json_action_id){
                case 102:   parsedAction = mapper.readValue(new TreeTraversingParser(jsonNode), MoveToLocationAction.class);
                    break;
                case 103:   parsedAction = mapper.readValue(new TreeTraversingParser(jsonNode), SpeakMessageAction.class);
                    break;
                case 104:   parsedAction = mapper.readValue(new TreeTraversingParser(jsonNode), ControlLockerAction.class);
                    break;
                default:    parsedAction = null;
                    break;
            }

            actions.add(parsedAction);
        }
        Log.i("JSON_PARSER", "Parsed actions: " + actions);

        return actions;
    }

    public Task parseJsonTask(JSONObject jsonTask) throws JSONException, IOException {

        int task_id = jsonTask.getInt("task_id");
        String taskName = jsonTask.getString("taskName");
        Date programmedDate = null;

        if(jsonTask.get("programmedDate") != null){

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try{
                programmedDate = sdf.parse(jsonTask.getString("programmedDate"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        JSONArray actionsArray = jsonTask.getJSONArray("actions");

        ArrayList<Action> parsedActions = parseJsonActions(actionsArray);

        if (programmedDate != null) {
            return new Task(task_id, taskName, parsedActions, programmedDate);
        } else {
            return new Task(task_id, taskName, parsedActions);
        }
    }

    public ArrayList<Task> parseJsonToTasks(String json) throws JSONException, IOException {

        ArrayList<Task> tasks = new ArrayList<>();
        JSONObject obj = new JSONObject(json);
        JSONArray TaskArray = obj.getJSONArray("tasks");

        for(int i = 0; i < TaskArray.length(); i++){
            tasks.add( parseJsonTask(TaskArray.getJSONObject(i)) );

        }
        return tasks;
    }

    public String jsonArraytest() throws JSONException, IOException {

        ArrayList<Task> tasks = parseJsonToTasks(json);

        Log.i("JSON", tasks.toString());

        return tasks.toString();
    }

    static JsonNode convertJsonFormat(JSONObject json) {
        ObjectNode ret = JsonNodeFactory.instance.objectNode();

        @SuppressWarnings("unchecked")
        Iterator<String> iterator = json.keys();
        for (; iterator.hasNext();) {
            String key = iterator.next();
            Object value;
            try {
                value = json.get(key);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            if (json.isNull(key))
                ret.putNull(key);
            else if (value instanceof String)
                ret.put(key, (String) value);
            else if (value instanceof Integer)
                ret.put(key, (Integer) value);
            else if (value instanceof Long)
                ret.put(key, (Long) value);
            else if (value instanceof Double)
                ret.put(key, (Double) value);
            else if (value instanceof Boolean)
                ret.put(key, (Boolean) value);
            else if (value instanceof JSONObject)
                ret.put(key, convertJsonFormat((JSONObject) value));
            else if (value instanceof JSONArray)
                ret.put(key, convertJsonFormat((JSONArray) value));
            else
                throw new RuntimeException("not prepared for converting instance of class " + value.getClass());
        }
        return ret;
    }

    static JsonNode convertJsonFormat(JSONArray json) {
        ArrayNode ret = JsonNodeFactory.instance.arrayNode();
        for (int i = 0; i < json.length(); i++) {
            Object value;
            try {
                value = json.get(i);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            if (json.isNull(i))
                ret.addNull();
            else if (value instanceof String)
                ret.add((String) value);
            else if (value instanceof Integer)
                ret.add((Integer) value);
            else if (value instanceof Long)
                ret.add((Long) value);
            else if (value instanceof Double)
                ret.add((Double) value);
            else if (value instanceof Boolean)
                ret.add((Boolean) value);
            else if (value instanceof JSONObject)
                ret.add(convertJsonFormat((JSONObject) value));
            else if (value instanceof JSONArray)
                ret.add(convertJsonFormat((JSONArray) value));
            else
                throw new RuntimeException("not prepared for converting instance of class " + value.getClass());
        }
        return ret;
    }

}


/*

{ "tasks": [{  "task_id": 1000000000,
              "taskName": "delivery",
              "programmedDate" : null,
              "actions" : [
                          { "action_id": 102,
                            "actionName": "moveTo",
                            "location": "locationName",
                            "speed": null,
                            "backwards":false
                          },
                          { "action_id": 103,
                            "actionName": "speakMessage",
                            "message": "pirupiru",
                            "language": "IT"
                          },
                          { "action_id": 104,
                            "actionName": "controlLock",
                            "state": "open",
                            "needsInput": false,
                            "code": null
                          }
                          ]
            }]
}

 */
