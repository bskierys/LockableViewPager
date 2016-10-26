package com.github.bskierys.sample.slides;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.bskierys.lockableviewpager.SwipeDirection;
import com.github.bskierys.lockableviewpager.sample.R;

import java.util.Locale;

/**
 * TODO: update doc
 */
public abstract class NumberedSlideFragment extends Fragment {
    static final String ARG_SLIDE_NUMBER = "slideNumber";
    static final String ARG_BACKGROUND_COLOR = "backgroundColor";

    int slideNumber;
    int backgroundColor;

    OnSlideInteractionListener listener;

    public NumberedSlideFragment() {
        // Required empty public constructor
    }

    void setArguments(Bundle args, int slideNumber, int backgroundColor) {
        args.putInt(ARG_SLIDE_NUMBER, slideNumber);
        args.putInt(ARG_BACKGROUND_COLOR, backgroundColor);
        super.setArguments(args);
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            slideNumber = getArguments().getInt(ARG_SLIDE_NUMBER);
            backgroundColor = getArguments().getInt(ARG_BACKGROUND_COLOR);
        }
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_numbered_slide, container, false);

        rootView.setBackgroundColor(backgroundColor);
        TextView slideNumberView = (TextView) rootView.findViewById(R.id.txt_slide_number);
        slideNumberView.setText(String.format(Locale.getDefault(), "%d", slideNumber + 1));
        FrameLayout subContainer = (FrameLayout) rootView.findViewById(R.id.container_sub);
        View subView = onCreateSubView(inflater, subContainer, savedInstanceState);
        try {
            subContainer.addView(subView);
        } catch (IllegalStateException error) {
            throw new IllegalStateException("Tried to add view that was previously added to container. If you are " +
                                                    "using inflater. Please do not attach view to root.", error);
        }

        return rootView;
    }

    protected abstract View onCreateSubView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSlideInteractionListener) {
            listener = (OnSlideInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnSlideInteractionListener");
        }
    }

    @Override public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void shown() {}

    public void hidden() {}

    /**
     * This interface must be implemented by activities that contain this fragment to allow an interaction in this
     * fragment to be communicated to the activity and potentially other fragments contained in that activity.
     * <p>
     * See the Android Training lesson <a href= "http://developer.android.com/training/basics/fragments/communicating
     * .html" >Communicating with Other Fragments</a> for more information.
     */
    public interface OnSlideInteractionListener {
        // TODO: Update argument type and name
        void lockPosition(int position, SwipeDirection direction);
        void unlockPosition(int position, SwipeDirection direction);
        SwipeDirection getLockedDirection(int position);
    }
}
