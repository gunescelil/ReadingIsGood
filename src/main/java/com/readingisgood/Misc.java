package com.readingisgood;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.readingisgood.exception.JsonSerializationException;

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
            throw new JsonSerializationException("Failed to serialize json object!", e);
        }

    }

    private Misc()
    {
    }
}
