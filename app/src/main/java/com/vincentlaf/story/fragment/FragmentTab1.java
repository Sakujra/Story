package com.vincentlaf.story.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Poi;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.vincentlaf.story.R;
import com.vincentlaf.story.bean.netbean.StoryListInfo;
import com.vincentlaf.story.others.App;
import com.vincentlaf.story.others.AuthorAdapter;
import com.vincentlaf.story.others.AuthorInformation;
import com.vincentlaf.story.others.MarkerCollection;
import com.vincentlaf.story.others.MarkerInfomation;
import com.vincentlaf.story.util.MarkerUtil;
import com.vincentlaf.story.util.ToastUtil;
import com.vincentlaf.story.others.WalkRouteOverlay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VincentLaf on 2017/12/17.
 */

public class FragmentTab1 extends Fragment implements RouteSearch.OnRouteSearchListener {

    private MapView mMapView;
    private AMap mAMap;
    private WalkRouteResult mWalkRouteResult;
    private Marker currentMarker = null;
    private View allView;
    private WalkRouteOverlay currentWRL = null;
    private MarkerCollection markerCollection=null;



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private final int ROUTE_TYPE_BUS = 1;
    private final int ROUTE_TYPE_DRIVE = 2;
    private final int ROUTE_TYPE_WALK = 3;
    private final int ROUTE_TYPE_CROSSTOWN = 4;
    private RouteSearch mRouteSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        initview(savedInstanceState, view);
        return view;
    }

    private void initview(Bundle savedInstanceState, final View view) {
        allView = view;
        mMapView = view.findViewById(R.id.z_map);
        mMapView.onCreate(savedInstanceState);
        if (mAMap == null) {
            mAMap = mMapView.getMap();
        }

        UiSettings uiSettings = mAMap.getUiSettings();
        uiSettings.setLogoBottomMargin(-50);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        //初始化定位蓝点样式类
        setLocation(mAMap);
        //设置地图范围
        setMapStatusLimits(mAMap, 114.332933, 30.514793, 114.391641, 30.5624);
        //设置手势
        setGesture(mAMap);
        //向服务器请求数据后添加Marker
//        final MarkerCollection markerCollection = new MarkerCollection(mAMap);
//        Object a = new MarkerInfomation("a");
//        markerCollection.addMarkerWithGrowAnimation(30.537615, 114.364966, a);
//        markerCollection.addMarkerWithGrowAnimation(30.537615, 114.364066, a);
//        markerCollection.addMarkerWithGrowAnimation(30.537615, 114.363066, a);
        //设置自定义窗口
        mAMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                /*if(infoWindow == null) {
                    infoWindow = LayoutInflater.from(getContext()).inflate(
                            R.layout.custom_info_window, null);
                }
                render(marker, infoWindow);
                return infoWindow;*/
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
//                View infoContent = LayoutInflater.from(getContext()).inflate(
//                        R.layout.custom_info_contents, null);
                View infoContent = LayoutInflater.from(getContext()).inflate(
                        R.layout.z_map_marker_active, null);
//                ((ImageView) infoContent.findViewById(R.id.badge))
//                        .setImageResource(R.drawable.badge_sa);
                render(marker, infoContent);
                return infoContent;
            }

            public void render(Marker marker, View view) {

                //如果想修改自定义Infow中内容，请通过view找到它并修改
//                String title = marker.getTitle();
//                TextView titleUi = ((TextView) view.findViewById(R.id.title));
//                if (title != null) {
//                    SpannableString titleText = new SpannableString(title);
//                    titleText.setSpan(new ForegroundColorSpan(Color.RED), 0,
//                            titleText.length(), 0);
//                    titleUi.setTextSize(15);
//                    titleUi.setText(titleText);
//
//                } else {
//                    titleUi.setText("");
//                }
//                MarkerInfomation markerInfomation = (MarkerInfomation) marker.getObject();
//                String snippet = markerInfomation.getPlaceName();
//                TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
//                if (snippet != null) {
//                    SpannableString snippetText = new SpannableString(snippet);
//                    snippetText.setSpan(new ForegroundColorSpan(Color.GREEN), 0,
//                            snippetText.length(), 0);
//                    snippetUi.setTextSize(20);
//                    snippetUi.setText(snippetText);
//                } else {
//                    snippetUi.setText("");
//                }

            }

        });
        mAMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {

                currentMarker = marker;
                for (int i = 0; i < markerCollection.getMarkerList().size(); i++) {
                   // markerCollection.setMarkerIcon((Marker) markerCollection.getMarkerList().get(i), MarkerUtil.getMarkerNormalBitmap());
                }
                //markerCollection.setMarkerIcon(marker, MarkerUtil.getMarkerActiveBitmap());
                Toast.makeText(getContext(), "您点击了Marker", Toast.LENGTH_LONG).show();
                marker.startAnimation();
                marker.showInfoWindow();
                initRecyclerView(view, getAuthorInformationList());
//                searchRouteResult(ROUTE_TYPE_WALK,RouteSearch.WALK_DEFAULT,
//                        new LatLonPoint(mAMap.getMyLocation().getLatitude(),mAMap.getMyLocation().getLongitude()),
//                        new LatLonPoint(marker.getPosition().latitude,marker.getPosition().longitude)
//                );

                return true;
            }
        });
        //地图poi点击功能
        final MarkerCollection markerCollection1 = new MarkerCollection(mAMap);
        mAMap.setOnPOIClickListener(new AMap.OnPOIClickListener() {
            @Override
            public void onPOIClick(Poi poi) {
//                markerCollection1.clearAll();
//                markerCollection1.addMarker(poi.getCoordinate().latitude, poi.getCoordinate().longitude, new MarkerInfomation(poi.getName()));
//                Marker marker = (Marker) markerCollection1.getMarkerList().get(0);
//                marker.showInfoWindow();
//                searchRouteResult(ROUTE_TYPE_WALK,RouteSearch.WALK_DEFAULT,
//                        new LatLonPoint(mAMap.getMyLocation().getLatitude(),mAMap.getMyLocation().getLongitude()),
//                        new LatLonPoint(marker.getPosition().latitude,marker.getPosition().longitude)
//                );
            }
        });
        mAMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
//                view.findViewById(R.id.z_recyclerview_frag1).setVisibility(View.INVISIBLE);
//                if (markerCollection.getMarkerList().size() > 0) {
//                    for (int i = 0; i < markerCollection.getMarkerList().size(); i++) {
//                        Marker marker = (Marker) markerCollection.getMarkerList().get(i);
//                        marker.hideInfoWindow();
//                    }
//                }
//                if (markerCollection1.getMarkerList().size() > 0) {
//                    for (int i = 0; i < markerCollection1.getMarkerList().size(); i++) {
//                        Marker marker = (Marker) markerCollection1.getMarkerList().get(i);
//                        marker.hideInfoWindow();
//                    }
//                }
            }
        });
        //步行路线规划
//        final Button button = view.findViewById(R.id.navigation_button);
//        button.setOnClickListener(new View.OnClickListener() {
//            boolean isSelected = false;
//
//            @SuppressLint("ResourceType")
//            @Override
//            public void onClick(View view) {
//                if (currentMarker != null) {
//                    if (!isSelected) {
//                        button.setBackgroundResource(R.drawable.z_item_storylist_bg_press);
//                        searchRouteResult(ROUTE_TYPE_WALK, RouteSearch.WALK_DEFAULT,
//                                new LatLonPoint(mAMap.getMyLocation().getLatitude(), mAMap.getMyLocation().getLongitude()),
//                                new LatLonPoint(currentMarker.getPosition().latitude, currentMarker.getPosition().longitude)
//                        );
//                        isSelected = true;
//                    } else {
//                        isSelected = false;
//                        button.setBackgroundResource(R.drawable.z_item_storylist_bg_normal);
//                        if (currentWRL != null) {
//                            currentWRL.removeFromMap();
//                        }
//                    }
//
//                } else {
//                    ToastUtil.toast("没有选中目的地");
//                }
//
//            }
//        });


        mRouteSearch = new RouteSearch(getContext());
        mRouteSearch.setRouteSearchListener(this);
    }

    public void addMarkers(List<StoryListInfo> storyListInfos){
        markerCollection=new MarkerCollection(mAMap);
        for (int i = 0; i <storyListInfos.size() ; i++) {
            StoryListInfo storyListInfo=storyListInfos.get(i);
            markerCollection.addMarkerWithGrowAnimation(storyListInfo.getLat(),storyListInfo.getLon(),storyListInfo);
        }
    }
    private void initRecyclerView(View view, List<AuthorInformation> authorInformationList) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.z_recyclerview_frag1);
        if (recyclerView.getVisibility() == View.INVISIBLE) {
            recyclerView.setVisibility(View.VISIBLE);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        AuthorAdapter adapter = new AuthorAdapter(authorInformationList);
        recyclerView.setAdapter(adapter);
    }

    private List getAuthorInformationList() {
        List<AuthorInformation> authorInformationList = new ArrayList<>();
        authorInformationList.add(new AuthorInformation("张世杰", R.drawable.avatar_1, "我牛逼", "我超级帅！"));
        authorInformationList.add(new AuthorInformation("张世杰", R.drawable.avatar_1, "我牛逼", "我超级帅！"));
        authorInformationList.add(new AuthorInformation("张世杰", R.drawable.avatar_1, "我牛逼", "我超级帅！"));
        authorInformationList.add(new AuthorInformation("张世杰", R.drawable.avatar_1, "我牛逼", "我超级帅！"));
        authorInformationList.add(new AuthorInformation("张世杰", R.drawable.avatar_1, "我牛逼", "我超级帅！"));
        authorInformationList.add(new AuthorInformation("张世杰", R.drawable.avatar_1, "我牛逼", "我超级帅！"));
        authorInformationList.add(new AuthorInformation("张世杰", R.drawable.avatar_1, "我牛逼", "我超级帅！"));
        return authorInformationList;
    }

    /**
     * 设置地图的显示范围
     *
     * @param aMap
     * @param sy   西南经度
     * @param sx   西南纬度
     * @param ny   东北经度
     * @param nx   东北纬度
     */
    private void setMapStatusLimits(AMap aMap, double sy, double sx, double ny, double nx) {
        // 西南坐标
        LatLng southwestLatLng = new LatLng(sx, sy);
        // 东北坐标
        LatLng northeastLatLng = new LatLng(nx, ny);
        LatLngBounds latLngBounds = new LatLngBounds(southwestLatLng, northeastLatLng);
        aMap.setMapStatusLimits(latLngBounds);
    }

    /**
     * 设置手势
     */
    private void setGesture(AMap aMap) {
        UiSettings mUiSettings = aMap.getUiSettings();
        mUiSettings.setRotateGesturesEnabled(false);//旋转
        mUiSettings.setTiltGesturesEnabled(false);//倾斜
    }

    /**
     * 设置地图定位
     *
     * @param aMap
     */
    private void setLocation(AMap aMap) {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);
        myLocationStyle.showMyLocation(true);
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                //从location对象中获取经纬度信息，地址描述信息，建议拿到位置之后调用逆地理编码接口获取
                Log.d("位置信息", location.toString());
            }
        });

    }

    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int mode, LatLonPoint mStartPoint, LatLonPoint mEndPoint) {
        if (mStartPoint.getLatitude() == 0.0) {
            mStartPoint = new LatLonPoint(114.364015, 30.534631);
        }
        if (mStartPoint == null) {
            ToastUtil.toast("起点未设置");
            return;
        }
        if (mEndPoint == null) {
            ToastUtil.toast("终点未设置");
        }
        //showProgressDialog();
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);
        if (routeType == ROUTE_TYPE_BUS) {// 公交路径规划
            RouteSearch.BusRouteQuery query = new RouteSearch.BusRouteQuery(fromAndTo, mode,
                    "wuhan", 0);// 第一个参数表示路径规划的起点和终点，第二个参数表示公交查询模式，第三个参数表示公交查询城市区号，第四个参数表示是否计算夜班车，0表示不计算
            mRouteSearch.calculateBusRouteAsyn(query);// 异步路径规划公交模式查询
        } else if (routeType == ROUTE_TYPE_DRIVE) {// 驾车路径规划
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null,
                    null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
            mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        } else if (routeType == ROUTE_TYPE_WALK) {// 步行路径规划
            RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo, mode);
            mRouteSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询
        } else if (routeType == ROUTE_TYPE_CROSSTOWN) {
            RouteSearch.FromAndTo fromAndTo_bus = new RouteSearch.FromAndTo(
                    mStartPoint, mEndPoint);
            RouteSearch.BusRouteQuery query = new RouteSearch.BusRouteQuery(fromAndTo_bus, mode,
                    "呼和浩特市", 0);// 第一个参数表示路径规划的起点和终点，第二个参数表示公交查询模式，第三个参数表示公交查询城市区号，第四个参数表示是否计算夜班车，0表示不计算
            query.setCityd("农安县");
            mRouteSearch.calculateBusRouteAsyn(query);// 异步路径规划公交模式查询
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int errorCode) {
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            // mAMap.clear();
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mWalkRouteResult = result;
                    final WalkPath walkPath = mWalkRouteResult.getPaths()
                            .get(0);
                    WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(
                            getContext(), mAMap, walkPath,
                            mWalkRouteResult.getStartPos(),
                            mWalkRouteResult.getTargetPos());
                    walkRouteOverlay.removeFromMap();
                    walkRouteOverlay.addToMap();
                    walkRouteOverlay.zoomToSpan();
                    currentWRL = walkRouteOverlay;
                } else if (result != null && result.getPaths() == null) {
                    ToastUtil.toast("GG");
                }

            } else {
                ToastUtil.toast("GG");
                ;
            }
        } else {
            ToastUtil.toast("错误代码" + errorCode);
        }
    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }


}
