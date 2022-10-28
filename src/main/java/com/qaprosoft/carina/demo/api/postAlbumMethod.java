package com.qaprosoft.carina.demo.api;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.carina.core.foundation.api.annotation.Endpoint;
import com.qaprosoft.carina.core.foundation.api.annotation.RequestTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.ResponseTemplatePath;
import com.qaprosoft.carina.core.foundation.api.annotation.SuccessfulHttpStatus;
import com.qaprosoft.carina.core.foundation.api.http.HttpMethodType;
import com.qaprosoft.carina.core.foundation.api.http.HttpResponseStatusType;
import com.qaprosoft.carina.core.foundation.utils.Configuration;

@Endpoint(url = "${base_url}/albums", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/albums/_post/rq.json")
@ResponseTemplatePath(path = "api/albums/_post/rs.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.CREATED_201)
public class postAlbumMethod extends AbstractApiMethodV2 {
    public postAlbumMethod() {
        super("api/albums/_post/rq.json", "api/albums/_post/rs.json", "api/albums/album.properties");
        replaceUrlPlaceholder("base_url", Configuration.getEnvArg("https://jsonplaceholder.typicode.com"));
    }

}
