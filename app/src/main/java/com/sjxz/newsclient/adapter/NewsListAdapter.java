package com.sjxz.newsclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sjxz.newsclient.BaseApplication;
import com.sjxz.newsclient.R;
import com.sjxz.newsclient.activity.NewsDetailActivity;
import com.sjxz.newsclient.bean.news.T1348647909107;
import com.sjxz.newsclient.util.Utils;
import com.sjxz.newsclient.view.ADPager;
import com.sjxz.newsclient.view.MultiImageView;
import com.sjxz.newsclient.view.MyBitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/3/13.
 * Role:
 */
public class NewsListAdapter extends BaseRecyclerAdapter implements ADPager.OnADPageClickListener {

    private List<String> listUrl;
    private Context context;
    private List<String> adPageList;

    public final static int TYPE_HEAD = -10;
    public final static int TYPE_NULL_ONE_IMAGE = 1;//没有图片货只有一张显示方式
    public final static int TYPE_BIG_IMAGE = 2;//存在图片显示方式---固定第三张显示大图
    public final static int TYPE_MANY_IMAGE = 3;//存在多图的情况


    /**
     * 需要点击列表传递过来的新闻详情链接
     */
    public static final String ARG_NEWS_URL = "arg_news_url";
    /**
     * 需要传递过来的新闻图片
     */
    public static final String ARG_NEWS_PIC = "arg_news_pic";
    /**
     * 需要传递过来的新闻标题
     */
    public static final String ARG_NEWS_TITLE = "arg_news_title";


    private  boolean isInitAD=false;


    public NewsListAdapter(Context context) {
        this.context = context;
    }

    public void setNewADpagerList(List<String> adPageList) {
        this.adPageList = adPageList;
    }

    @Override
    public void onPageClick(int page) {
        if (adPageList != null && page >= 0 && page < adPageList.size() && context != null) {
//           String item = adPageList.get(page);
//
//            Intent intent = new Intent(context, NewsDetailActivity.class);
//            if((item != null )) {
//                intent.putExtra(NewsDetailActivity.ARG_NEWS_PIC, item);
//            }
//            intent.putExtra(NewsDetailActivity.ARG_NEWS_URL, item.link);
//            intent.putExtra(NewsDetailActivity.ARG_NEWS_TITLE, item.title);
//            context.startActivity(intent);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rl_viewpager;

        ADPager news_pager;

        TextView news_title;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            news_pager = (ADPager) itemView.findViewById(R.id.news_pager);

            news_title = (TextView) itemView.findViewById(R.id.news_title);

            rl_viewpager = (RelativeLayout) itemView.findViewById(R.id.rl_viewpager);

        }
    }


    public class NewsListHolder extends RecyclerView.ViewHolder {

        public LinearLayout ll_root;

        public ImageView news_icon;

        public TextView news_title;

        public TextView news_desc;

        public TextView news_comefrom;

        public TextView news_time;

        public int viewType;


        public MultiImageView big_icon_multiImagView;

        public TextView big_news_title;

        public TextView news_comefrom_big;

        public TextView news_time_big;


        public TextView news_comefrom_three;

        public TextView news_time_three;

        public MultiImageView multiImagView;

        public TextView grid_news_title;


        public NewsListHolder(View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;

            ll_root = (LinearLayout) itemView.findViewById(R.id.ll_root);

            ViewStub viewStub = (ViewStub) itemView.findViewById(R.id.viewStub);

            switch (viewType) {

                case TYPE_NULL_ONE_IMAGE:

                    viewStub.setLayoutResource(R.layout.layout_news_holder);
                    viewStub.inflate();
//                    CardView card_view = (CardView) itemView.findViewById(R.id.card_view);
//                    if (card_view != null) {
//                        rootView = card_view;
                    news_icon = (ImageView) itemView.findViewById(R.id.news_icon);
                    news_title = (TextView) itemView.findViewById(R.id.news_title);
                    news_desc = (TextView) itemView.findViewById(R.id.news_desc);
                    news_comefrom = (TextView) itemView.findViewById(R.id.news_comefrom);
                    news_time = (TextView) itemView.findViewById(R.id.news_time);
//                    }
                    break;

                case TYPE_BIG_IMAGE:
                    viewStub.setLayoutResource(R.layout.layout_news_big_image_holder);
                    viewStub.inflate();
                    big_icon_multiImagView = (MultiImageView) itemView.findViewById(R.id.big_icon_multiImagView);
                    big_news_title = (TextView) itemView.findViewById(R.id.big_news_title);
                    news_comefrom_big = (TextView) itemView.findViewById(R.id.news_comefrom_big);
                    news_time_big = (TextView) itemView.findViewById(R.id.news_time_big);
                    break;

                case TYPE_MANY_IMAGE:
                    viewStub.setLayoutResource(R.layout.layout_news_grid_holder);
                    viewStub.inflate();
                    multiImagView = (MultiImageView) itemView.findViewById(R.id.multiImagView);
                    grid_news_title = (TextView) itemView.findViewById(R.id.grid_news_title);
                    news_comefrom_three = (TextView) itemView.findViewById(R.id.news_comefrom_three);
                    news_time_three = (TextView) itemView.findViewById(R.id.news_time_three);
                    break;
            }


        }
    }


    @Override
    public RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
        return new NewsListHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, boolean isItem) {
        //确定有头标记记录
        if (getItemViewType(position) == TYPE_HEAD) {

            final HeaderViewHolder headHolder = (HeaderViewHolder) viewHolder;

            headHolder.rl_viewpager.setLayoutParams(new LinearLayout.LayoutParams(Utils.getScreenWidth(context), Utils.getScreenWidth(context) / 2));

            if (null != adPageList&&!isInitAD) {
                headHolder.news_pager.setImageUrl(adPageList);
                headHolder.news_pager.setAutoPlay(true, 3000);
                headHolder.news_pager.setOnPageClickListener(this);
                isInitAD=true;
            }


        } else {


            final NewsListHolder holder = (NewsListHolder) viewHolder;

            final T1348647909107 item = (T1348647909107) getDatas().get(position - 1);


            holder.ll_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(item.getUrl()==null){
                        return ;
                    }
                    Intent intent = new Intent(context, NewsDetailActivity.class);
                    if((item.getImgsrc() != null )) {
                        intent.putExtra(NewsDetailActivity.ARG_NEWS_PIC, item.getImgsrc());
                    }
                    intent.putExtra(NewsDetailActivity.ARG_NEWS_URL, item.getUrl());
                    intent.putExtra(NewsDetailActivity.ARG_NEWS_TITLE, item.getTitle());
                    context.startActivity(intent);
                }
            });


            switch (holder.viewType) {
                case TYPE_NULL_ONE_IMAGE:
                    if (item.getImgsrc() != null) {
                        Glide.with(BaseApplication.getApplication()).load(item.getImgsrc()).asBitmap().
                                diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().
                                placeholder(R.mipmap.homebg).into(new MyBitmapImageViewTarget(holder.news_icon));
                    } else {
                        holder.news_icon.setImageResource(R.mipmap.noimage);
                    }

                    holder.news_desc.setText(item.getDigest());

                    holder.news_title.setText(item.getTitle());

                    holder.news_comefrom.setText(item.getSource());

                    try {
//                        Date createTime = Utils.df.parse(item.pubDate);
                        holder.news_time.setText(item.getPtime() + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    break;

                case TYPE_BIG_IMAGE:

                    holder.news_comefrom_big.setText(item.getSource());

                    try {
//                        Date createTime = Utils.df.parse(item.pubDate);
                        holder.news_time_big.setText(item.getPtime() + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    holder.big_news_title.setText(item.getTitle());

                    if (item.getImgsrc() != null) {
//                        Glide.with(BaseApplication.getApplication()).load(item.getImgsrc()).asBitmap().
//                                diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().
//                                placeholder(R.mipmap.homebg).into(new MyBitmapImageViewTarget(holder.big_icon));
                        List<String> urls=new ArrayList<>();
                        urls.add(item.getImgsrc());
                        holder.big_icon_multiImagView.setList(urls);

                        holder.big_icon_multiImagView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                if(item.getUrl()==null){
                                    return ;
                                }
                                Intent intent = new Intent(context, NewsDetailActivity.class);
                                if((item.getImgsrc() != null )) {
                                    intent.putExtra(NewsDetailActivity.ARG_NEWS_PIC, item.getImgsrc());
                                }
                                intent.putExtra(NewsDetailActivity.ARG_NEWS_URL, item.getUrl());
                                intent.putExtra(NewsDetailActivity.ARG_NEWS_TITLE, item.getTitle());
                                context.startActivity(intent);
                            }
                        });
                    } else {
//                        holder.big_icon.setImageResource(R.mipmap.noimage);
                    }
                    break;

                case TYPE_MANY_IMAGE:

                    holder.grid_news_title.setText(item.getTitle());

                    holder.news_comefrom_three.setText(item.getSource());

                    try {
//                        Date createTime = Utils.df.parse(item.pubDate);
                        holder.news_time_three.setText(item.getPtime() + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    listUrl = new ArrayList<>();

                    if (item.getImgextra().size() > 3) {
                        for (int i = 0; i < 3; i++) {
                            listUrl.add(item.getImgextra().get(i).getImgsrc());
                        }
                    } else {
                        for (int i = 0; i < item.getImgextra().size(); i++) {
                            listUrl.add(item.getImgextra().get(i).getImgsrc());
                        }
                    }

                    holder.multiImagView.setList(listUrl);
                    holder.multiImagView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            if(item.getUrl()==null){
                                return ;
                            }
                            Intent intent = new Intent(context, NewsDetailActivity.class);
                            if((item.getImgsrc() != null )) {
                                intent.putExtra(NewsDetailActivity.ARG_NEWS_PIC, item.getImgsrc());
                            }
                            intent.putExtra(NewsDetailActivity.ARG_NEWS_URL, item.getUrl());
                            intent.putExtra(NewsDetailActivity.ARG_NEWS_TITLE, item.getTitle());
                            context.startActivity(intent);
                        }
                    });
                    break;
            }
        }
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD) {
            return LayoutInflater.from(context).inflate(R.layout.layout_news_head, parent, false);
        }
        return LayoutInflater.from(context).inflate(R.layout.layout_news_three_status_holder, parent, false);
    }

    @Override
    public RecyclerView.ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        if (viewType == TYPE_HEAD) {
            return new HeaderViewHolder(realContentView);
        }
        return new NewsListHolder(realContentView, viewType);
    }

    @Override
    public int getAdapterItemCount() {
        return getDatas().size() + 1;
    }

    @Override
    public int getAdapterItemViewType(int position) {
//        if (isHeader(position)) {
//            return VIEW_TYPES.HEADER;
//        } else if (isFooter(position)) {
//            return VIEW_TYPES.FOOTER;
        if (position == 0) {
            return TYPE_HEAD;
//
        } else {
//            position = getStart() > 0 ? position - 1 : position;
            position = position - 1;

            T1348647909107 item = ((T1348647909107) getDatas().get(position));

            int itemType = 0;

            if ((item.getImgextra() != null && item.getImgextra().size() == 1 || (item.getImgextra() == null && item.getImgsrc() != null))
                    && position % 7 == 0) {
                //模拟间隔处理大图数据
                itemType = TYPE_BIG_IMAGE;
            } else if ((item.getImgextra() != null && (item.getImgextra().size() == 1 || item.getImgextra().size() == 0))
                    || (item.getImgextra() == null && item.getImgsrc() != null)) {
                //只有一张图片或没有图片的情况的情况
                itemType = TYPE_NULL_ONE_IMAGE;
            } else if (item.getImgextra() != null && item.getImgextra().size() > 1) {
                //多图的情况
                itemType = TYPE_MANY_IMAGE;
            }

            return itemType;
        }
    }
}
