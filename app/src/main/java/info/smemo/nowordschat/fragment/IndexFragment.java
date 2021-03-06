package info.smemo.nowordschat.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import info.smemo.nbaseaction.adapter.NBaseBindingAdapter;
import info.smemo.nbaseaction.base.NBaseFragment;
import info.smemo.nowordschat.R;
import info.smemo.nowordschat.activity.ChatActivity;
import info.smemo.nowordschat.bean.MessageBean;
import info.smemo.nowordschat.contract.IndexContract;
import info.smemo.nowordschat.BR;

import static com.google.common.base.Preconditions.checkNotNull;

public class IndexFragment extends NBaseFragment implements IndexContract.View {

    private IndexContract.Presenter mPresenter;

    private NBaseBindingAdapter messageAdapter;
    private ArrayList<MessageBean> messageBeanArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageBeanArrayList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        messageAdapter = new NBaseBindingAdapter<>(messageBeanArrayList, BR.bean, R.layout.item_message);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.message_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(messageAdapter);

        initAdapter();

        return view;
    }

    private void initAdapter() {
        messageAdapter.setListener(new NBaseBindingAdapter.OnAdapterClickListener<MessageBean>() {
            @Override
            public void onClick(View view, int position, MessageBean object) {
                showChat(object);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showMessageList(ArrayList<MessageBean> list) {
        messageBeanArrayList.clear();
        for (MessageBean messageBean : list) {
            messageBeanArrayList.add(messageBean);
        }
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        messageAdapter.notifyDataSetChanged();
    }

    @Override
    public void showChat(MessageBean object) {
        startActivity(new Intent(getActivity(), ChatActivity.class));
    }

    @Override
    public void setPresenter(IndexContract.Presenter presenter) {
        this.mPresenter = checkNotNull(presenter);
    }
}
