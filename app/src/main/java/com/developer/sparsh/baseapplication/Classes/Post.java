package com.developer.sparsh.baseapplication.Classes;

import java.util.Date;

/**
 * Created by utkarshnath on 05/01/17.
 */

public class Post {
    public String uploaded_by;
    public Date uploaded_at;
    public String post_id;
    public String file_url;
    public int no_of_likes;
    public String discription;
    public String uploader_dp_url;
    public String type;
    public Comment[] comments;
}
