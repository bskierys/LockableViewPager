/*
* author: Bartlomiej Kierys
* date: 2016-10-16
* email: bskierys@gmail.com
*/
package com.github.bskierys.sample.slides;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.bskierys.lockableviewpager.SwipeDirection;
import com.github.bskierys.lockableviewpager.sample.R;
import com.github.bskierys.sample.NavigationButton;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * TODO: Generic description. Replace with real one.
 */
public class LockAnyPositionSlideFragment extends NumberedSlideFragment {
    private static final String ARG_BLOCKABLE_POSITIONS = "blockable_positions";

    @BindView(R.id.container) LinearLayout positionsContainer;
    @BindView(R.id.txt_advice) TextView adviceView;

    @BindView(R.id.choose_position_part) ExpandableLayout choosePositionPart;
    @BindView(R.id.choose_side_part) ExpandableLayout chooseSidePart;
    ChooseSidePopFragment chooseSideDialog;

    private int[] blockablePositions;

    public static LockAnyPositionSlideFragment newInstance(int slideNumber, int backgroundColor, int[]
            blockablePositions) {
        LockAnyPositionSlideFragment fragment = new LockAnyPositionSlideFragment();
        fragment.setArguments(new Bundle(), slideNumber, backgroundColor, blockablePositions);
        return fragment;
    }

    void setArguments(Bundle args, int slideNumber, int backgroundColor, int[] blockablePositions) {
        args.putIntArray(ARG_BLOCKABLE_POSITIONS, blockablePositions);
        super.setArguments(args, slideNumber, backgroundColor);
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            blockablePositions = getArguments().getIntArray(ARG_BLOCKABLE_POSITIONS);
        }
    }

    @Override protected View onCreateSubView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sub_fragment_lock_any_position, container, false);
        ButterKnife.bind(this, rootView);

        adviceView.setText("wybierz pozycję, którą chcesz zablokować");
        chooseSideDialog = (ChooseSidePopFragment) getChildFragmentManager().findFragmentById(R.id.choose_side_pop);
        chooseSidePart.hide(false);

        int rowIndex = 0;

        // TODO: 2016-10-25 refactor
        while ( rowIndex * 3 + 1<blockablePositions.length) {
            View rowRoot = inflater.inflate(R.layout.row_lock_position, positionsContainer, false);
            for (int i = 0; i < 3; i++) {
                final int positionNumber = rowIndex * 3 + i;
                if (blockablePositions.length > positionNumber) {
                    String identifier = "btn_" + Integer.toString(i + 1);
                    int id = getResources().getIdentifier(identifier, "id", this.getContext().getPackageName());
                    NavigationButton button = ButterKnife.findById(rowRoot, id);
                    button.setText(String.format(Locale.getDefault(), "%d", positionNumber + 1));
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override public void onClick(View view) {
                            onButtonClick(view, positionNumber);

                            int[] l = new int[2];
                            view.getLocationOnScreen(l);
                            Rect rect = new Rect(l[0], l[1], l[0] + view.getWidth(), l[1] + view.getHeight());

                            Log.d("touch", String.valueOf(rect.centerX()) + "x" + String.valueOf(rect.bottom));
                        }
                    });
                }
            }
            positionsContainer.addView(rowRoot);
            rowIndex++;
        }

        return rootView;
    }

    @Override public void hidden() {
        chooseSidePart.hide(true);
        choosePositionPart.show(true);
    }

    private void onButtonClick(View sender, final int position) {
        Log.d("lockPosition", String.format("Position %d clicked", position));

        chooseSidePart.show(true);
        choosePositionPart.hide(true);
        chooseSideDialog.setPosition(position, new ChooseSidePopFragment.PopInteractionListener() {
            @Override public void lockDirection(SwipeDirection direction) {
                listener.lockPosition(position, direction);
            }

            @Override public void unlockDirection(SwipeDirection direction) {
                listener.unlockPosition(position, direction);
            }

            @Override public SwipeDirection getLockedDirection() {
                return listener.getLockedDirection(position);
            }

            @Override public void closePop() {
                chooseSidePart.hide(true);
                choosePositionPart.show(true);
            }
        });
    }
}
