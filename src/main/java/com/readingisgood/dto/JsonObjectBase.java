package com.readingisgood.dto;

import java.io.Serializable;

import com.readingisgood.Misc;

public class JsonObjectBase implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -1222865119522538259L;

    @Override
    public String toString()
    {
        return Misc.serializeSafeJson(this);
    }
}