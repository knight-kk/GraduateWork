package cn.edu.abc.graduatework.ui.fragment.graduate;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.edu.abc.graduatework.R;
import cn.edu.abc.graduatework.util.StringUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class GraduateInfoFragment extends Fragment {
    public static final String CONTENT_TEXT = "content_text";
    @BindView(R.id.tv_info)
    TextView mTvInfo;
    Unbinder unbinder;


    public GraduateInfoFragment() {
        // Required empty public constructor
    }

    public static GraduateInfoFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString(CONTENT_TEXT, content);
        GraduateInfoFragment fragment = new GraduateInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graduate, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            String text = getArguments().getString(CONTENT_TEXT);
            if (!StringUtil.isEmpty(text)) {
                mTvInfo.setText(text);
            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
