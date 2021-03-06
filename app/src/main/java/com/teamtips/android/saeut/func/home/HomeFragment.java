package com.teamtips.android.saeut.func.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.teamtips.android.saeut.R;
import com.teamtips.android.saeut.TimberLogger;

import timber.log.Timber;

public class HomeFragment extends Fragment {

  private static final String ARG_ID = "arg_id";

  public static HomeFragment newInstance(int id) {
    Bundle bundle = new Bundle();
    bundle.putInt(ARG_ID, id);
    HomeFragment homeFragment = new HomeFragment();
    homeFragment.setArguments(bundle);
    return homeFragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getLifecycle().addObserver(new TimberLogger(this));
  }

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_home, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    int id = requireArguments().getInt(ARG_ID);
    Timber.d("param as id:%s", id);
  }
}
