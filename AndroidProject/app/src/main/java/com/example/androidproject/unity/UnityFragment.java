package com.example.androidproject.unity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UnityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UnityFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UnityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UnityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UnityFragment newInstance(String param1, String param2) {
        UnityFragment fragment = new UnityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private View playerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        playerView = UnityScene.mUnityPlayer.getView();
        //具体参数 跟自己公司Unity开发人员协商
        //第一个参数是unity那边的挂载脚本名字
        //第二个参数是 unity提供的方法名
        //第三个参数是 自己要给unity传的值
//        UnityScene.mUnityPlayer.UnitySendMessage("Main Camera","Id","1");

        return playerView;

    }
}