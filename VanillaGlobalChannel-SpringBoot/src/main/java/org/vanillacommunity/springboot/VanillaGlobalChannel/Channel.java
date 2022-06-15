package org.vanillacommunity.springboot.VanillaGlobalChannel;

import lombok.Data;

@Data
public class Channel {

    private final int id;
    private final String displayName;
    private String announcement;

    public Channel(int id,String displayName)
    {
        this.id = id;
        this.displayName = displayName;
        this.announcement = "频道内与他人交流时请注意礼貌哦！|禁止在频道内打广告，违规引流。";
    }
    @Override
    public String toString()
    {
        return id+"/"+displayName+"/"+announcement;
    }
}
