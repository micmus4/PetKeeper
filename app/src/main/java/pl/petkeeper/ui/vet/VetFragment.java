package pl.petkeeper.ui.vet;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.maps.model.PlacesSearchResult;

import java.util.Locale;

import pl.petkeeper.R;
import pl.petkeeper.databinding.FragmentVetBinding;
import pl.petkeeper.utils.NearbySearch;

public class VetFragment extends Fragment {

    private FragmentVetBinding binding;
    private VetViewModel vetViewModel;
    private NavController navController;
    private LatLng position;

    public VetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Places.isInitialized()) {
            Places.initialize(getContext(), "AIzaSyBWlRzD7iMsf2lTVPDmWHMUXVjwL9C3og8", Locale.getDefault());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vetViewModel =
                new ViewModelProvider(this).get(VetViewModel.class);
        binding = FragmentVetBinding.inflate(inflater, container, false);

        View root = binding.getRoot();


        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.googlemap);



        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                PlacesClient placesClient = Places.createClient(getContext());
                final boolean[] moved = {false};

                GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location location) {
                        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                        googleMap.addMarker(new MarkerOptions().position(loc).title("U R Here"));
                        if(googleMap != null & moved[0] == false){
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                            moved[0] = true;
                            findVets(location);
                        }
                    }

                    public void findVets(Location location) {
                        PlacesSearchResult[] placesSearchResults = new NearbySearch().run(
                                new com.google.maps.model.LatLng(location.getLatitude(),
                                        location.getLongitude())).results;

                        if (placesSearchResults != null )
                        {
                            for (PlacesSearchResult address: placesSearchResults)
                            {
                                LatLng vetLatLng = new LatLng(address.geometry.location.lat,
                                        address.geometry.location.lng);
                                MarkerOptions markerOptions = new MarkerOptions();

                                markerOptions.title("Veterinary");

                                markerOptions.position(vetLatLng);

                                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                                markerOptions.title(vetLatLng.latitude + ":" + vetLatLng.longitude);

                                googleMap.addMarker(markerOptions);
                            }
                        }
                        else{
                            TextView textView = root.findViewById( R.id.closestVetDistance );
                            textView.setText( "Not found sadge" );
                        }
                    }
                };


                if (ActivityCompat.checkSelfPermission(root.getContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(root.getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION }, 102);
                }
                else
                {
                googleMap.setMyLocationEnabled(true);
                googleMap.setOnMyLocationChangeListener( myLocationChangeListener );
                }

            }
        });
        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController( view );
    }
}