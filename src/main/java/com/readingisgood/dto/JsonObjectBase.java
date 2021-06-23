package com.readingisgood.dto;

import com.readingisgood.Misc;

public class JsonObjectBase
{
    @Override
    public String toString()
    {
        return Misc.serializeSafeJson(this);
    }
}