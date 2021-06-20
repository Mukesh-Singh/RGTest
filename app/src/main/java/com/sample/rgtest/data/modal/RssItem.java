package com.sample.rgtest.data.modal;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
/**
 * @author Mukesh
 * Pojo class to handle the data of 'item' node of the response
 */
@Root(name = "item", strict = false)
public class RssItem {
    @Element
    public String title;

    @Element
    public String link;

    @Element
    public String description;

    @Element
    public String pubDate;

    //@Element (name = "media:content" )
//    @ElementList
//    public MediaContent content;

    @Path("enclosure")
    @Attribute(name = "url", required = false)
    public String imageUrl = "";
}
