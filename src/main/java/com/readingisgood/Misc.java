package com.readingisgood;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Misc
{
    public static String serializeSafeJson(Object obj)
    {
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            return mapper.writeValueAsString(obj);

        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException("Failed to serialize json object!", e);
        }

    }
    
    private Misc() {}
}
