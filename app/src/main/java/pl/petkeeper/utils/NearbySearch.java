package pl.petkeeper.utils;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import com.google.maps.model.RankBy;
import com.google.maps.model.LatLng;
import java.io.IOException;

public class NearbySearch {

    public PlacesSearchResponse run(LatLng latLng){
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("keyhere")
                .build();

        try {
             PlacesSearchResponse request = PlacesApi.nearbySearchQuery(context, latLng)
                     .radius(2000)
                     .rankby(RankBy.PROMINENCE)
                     .keyword("weterynarz")
                     .type(PlaceType.VETERINARY_CARE)
                     .await();
             return request;
        } catch (ApiException | IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
