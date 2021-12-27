package com.example.book.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.book.R;
import com.example.book.bean.BookBean;

import java.util.List;

public class Adapter extends BaseAdapter {
    private Context context;
    private List<BookBean> list;

    public void setList(List<BookBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public BookAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return list == null ? 0 :list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.book_item_layout,null);
            viewHolder  = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) view.getTag();
        }
        BookBean bookInfo = (BookBean) getItem(position);
        viewHolder.tvBookName.setText(bookInfo.getName());
        viewHolder.tvBookAuthor.setText(bookInfo.getAuthor());
        return view;
    }
    class ViewHolder{
        TextView tvBookName;
        TextView tvBookAuthor;
        public ViewHolder(View view){

            tvBookName=(TextView) view.findViewById(R.id.item_name);
            tvBookAuthor=(TextView) view.findViewById(R.id.item_author);
        }
    }
}
