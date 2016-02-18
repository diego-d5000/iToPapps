package edu.diegod5000.itopapps.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import edu.diegod5000.itopapps.R;
import edu.diegod5000.itopapps.services.models.App;

public class AppDetailFragment extends Fragment {
    public static final String ARG_ITEM_APP = "item_app";
    private App app;
    public AppDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_APP)) {
            app = getArguments().getParcelable(ARG_ITEM_APP);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(app.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.app_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (app != null) {
            ((TextView) rootView.findViewById(R.id.textAppName)).setText(app.getName());
            ((TextView) rootView.findViewById(R.id.textAppAuthor)).setText(app.getArtist());
            ((TextView) rootView.findViewById(R.id.textAppCategory)).setText(app.getCategory());
            ((TextView) rootView.findViewById(R.id.textAppInfo)).setText(app.getSummary());
            ((TextView) rootView.findViewById(R.id.textAppRights)).setText(app.getRights());
            ((SimpleDraweeView) rootView.findViewById(R.id.simpleDraweeImgCard)).setImageURI(Uri.parse(app.getImageUrl()));
            Button buttonGetApp = (Button) rootView.findViewById(R.id.buttonGetApp);
            if (app.getPrice() != null) {
                buttonGetApp.setText(app.getPrice());
            } else buttonGetApp.setText(getString(R.string.free));
            buttonGetApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(app.getDownloadUrl()));
                    startActivity(browserIntent);
                }
            });
        }

        return rootView;
    }
}
