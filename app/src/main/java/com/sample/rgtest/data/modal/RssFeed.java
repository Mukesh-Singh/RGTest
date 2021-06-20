package com.sample.rgtest.data.modal;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
/**
 * @author Mukesh
 * Pojo class to handle the data of 'rss' node of the response
 */
@Root(name = "rss", strict = false)
public class RssFeed {
    @Element
    public RssChannel channel;

    @Override
    public String toString() {
        return "RssFeed [channel=" + channel + "]";
    }
}
