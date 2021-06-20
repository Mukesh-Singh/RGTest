package com.sample.rgtest.data.modal;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * @author Mukesh
 * Pojo class to handle the data of 'channel' node of the response
 */
@Root(name = "channel", strict = false)
public class RssChannel {
    @Element
    public String title;

    @ElementList(inline = true, required = false)
    public List<RssItem> item;
}
