package com.ownblog.caelog.Dao;

import com.ownblog.caelog.pojo.Announcement;

import java.util.HashMap;
import java.util.List;

public interface AnnouncementDao {
    List<Announcement> getAnnouncementlist(HashMap map);
    void insertAnnouncement(Announcement announcement);
    void deleteAnnouncement(HashMap map);
    void updateAnnouncement(HashMap map);
    List<Announcement> getAnnouncementlistbyindex(HashMap map);
    List<Announcement> getAnnouncementlistbyid(HashMap map);
}
