package com.example.fragment.content;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.application.MaimobApplication;
import com.example.object.mFragmentManage;
import com.example.tab.R;
import com.example.util.ImageUtil;
import com.example.util.MyLogger;
import com.example.util.SerUser;
import com.example.util.SharedPreferencesUtils;
import com.example.util.StringUtils;
import com.example.util.User;

public class My_Userinfo_Edit extends Fragment implements OnClickListener{
	private View view;
	private RelativeLayout layout_icon, layout_name, layout_Signature, layout_sex, layout_address;
	private ImageView iv_Icon;
	private TextView tv_Name, tv_Signature, tv_sex, tv_address;
	private boolean needRefresh = false;
	private AlertDialog.Builder builder_Pic;
	private ArrayAdapter<String> adapter_Pic;
	private final String[] items = new String[]{"拍照" , "从相册选择"};
	private Uri mImageCaptureUri;
	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_FILE = 2;
	private static final int PHOTO_REQUEST_CUT = 3;// 剪切结果
	private String currImg = null;
	private AlertDialog dialog;
	private MyLogger logger = MyLogger.jLog();
	private User user;
	private int selectIndex = 0;
	private PopupWindow pop;
	private TextView tv_address_result;
	private Button bt_confirm, bt_cancel;
	private Spinner province_spinner;
	private Spinner city_spinner;
	private Spinner county_spinner;
	private Integer provinceId, cityId;
	private String mCounty, mCity, mProvince;
	
	//省市选择列表所需资源
	private int[] city = { R.array.beijin_province_item,
			R.array.shanghai_province_item, R.array.guangdong_province_item,
			R.array.shanxi1_province_item, R.array.neimenggu_province_item,
			R.array.liaoning_province_item, R.array.jilin_province_item,
			R.array.heilongjiang_province_item, R.array.tianjin_province_item,
			R.array.jiangsu_province_item, R.array.zhejiang_province_item,
			R.array.anhui_province_item, R.array.fujian_province_item,
			R.array.jiangxi_province_item, R.array.shandong_province_item,
			R.array.henan_province_item, R.array.hubei_province_item,
			R.array.hunan_province_item, R.array.heibei_province_item,
			R.array.guangxi_province_item, R.array.hainan_province_item,
			R.array.chongqing_province_item, R.array.sichuan_province_item,
			R.array.guizhou_province_item, R.array.yunnan_province_item,
			R.array.xizang_province_item, R.array.shanxi2_province_item,
			R.array.gansu_province_item, R.array.qinghai_province_item,
			R.array.linxia_province_item, R.array.xinjiang_province_item,
			R.array.hongkong_province_item, R.array.aomen_province_item,
			R.array.taiwan_province_item };
	private int[] countyOfBeiJing = { R.array.beijin_city_item };
	private int[] countyOfTianJing = { R.array.tianjin_city_item };
	private int[] countyOfHeBei = { R.array.shijiazhuang_city_item,
			R.array.tangshan_city_item, R.array.qinghuangdao_city_item,
			R.array.handan_city_item, R.array.xingtai_city_item,
			R.array.baoding_city_item, R.array.zhangjiakou_city_item,
			R.array.chengde_city_item, R.array.cangzhou_city_item,
			R.array.langfang_city_item, R.array.hengshui_city_item };
	private int[] countyOfShanXi1 = { R.array.taiyuan_city_item,
			R.array.datong_city_item, R.array.yangquan_city_item,
			R.array.changzhi_city_item, R.array.jincheng_city_item,
			R.array.shuozhou_city_item, R.array.jinzhong_city_item,
			R.array.yuncheng_city_item, R.array.xinzhou_city_item,
			R.array.linfen_city_item, R.array.lvliang_city_item };
	private int[] countyOfNeiMengGu = { R.array.huhehaote_city_item,
			R.array.baotou_city_item, R.array.wuhai_city_item,
			R.array.chifeng_city_item, R.array.tongliao_city_item,
			R.array.eerduosi_city_item, R.array.hulunbeier_city_item,
			R.array.bayannaoer_city_item, R.array.wulanchabu_city_item,
			R.array.xinganmeng_city_item, R.array.xilinguolemeng_city_item,
			R.array.alashanmeng_city_item };
	private int[] countyOfLiaoNing = { R.array.shenyang_city_item,
			R.array.dalian_city_item, R.array.anshan_city_item,
			R.array.wushun_city_item, R.array.benxi_city_item,
			R.array.dandong_city_item, R.array.liaoning_jinzhou_city_item,
			R.array.yingkou_city_item, R.array.fuxin_city_item,
			R.array.liaoyang_city_item, R.array.panjin_city_item,
			R.array.tieling_city_item, R.array.zhaoyang_city_item,
			R.array.huludao_city_item };
	private int[] countyOfJiLin = { R.array.changchun_city_item,
			R.array.jilin_city_item, R.array.siping_city_item,
			R.array.liaoyuan_city_item, R.array.tonghua_city_item,
			R.array.baishan_city_item, R.array.songyuan_city_item,
			R.array.baicheng_city_item, R.array.yanbian_city_item };
	private int[] countyOfHeiLongJiang = { R.array.haerbing_city_item,
			R.array.qiqihaer_city_item, R.array.jixi_city_item,
			R.array.hegang_city_item, R.array.shuangyashan_city_item,
			R.array.daqing_city_item, R.array.heilongjiang_yichun_city_item,
			R.array.jiamusi_city_item, R.array.qitaihe_city_item,
			R.array.mudanjiang_city_item, R.array.heihe_city_item,
			R.array.suihua_city_item, R.array.daxinganling_city_item };
	private int[] countyOfShangHai = { R.array.shanghai_city_item };
	private int[] countyOfJiangSu = { R.array.nanjing_city_item,
			R.array.wuxi_city_item, R.array.xuzhou_city_item,
			R.array.changzhou_city_item, R.array.nanjing_suzhou_city_item,
			R.array.nantong_city_item, R.array.lianyungang_city_item,
			R.array.huaian_city_item, R.array.yancheng_city_item,
			R.array.yangzhou_city_item, R.array.zhenjiang_city_item,
			R.array.jiangsu_taizhou_city_item, R.array.suqian_city_item };
	private int[] countyOfZheJiang = { R.array.hangzhou_city_item,
			R.array.ningbo_city_item, R.array.wenzhou_city_item,
			R.array.jiaxing_city_item, R.array.huzhou_city_item,
			R.array.shaoxing_city_item, R.array.jinhua_city_item,
			R.array.quzhou_city_item, R.array.zhoushan_city_item,
			R.array.zejiang_huzhou_city_item, R.array.lishui_city_item };
	private int[] countyOfAnHui = { R.array.hefei_city_item,
			R.array.wuhu_city_item, R.array.bengbu_city_item,
			R.array.huainan_city_item, R.array.maanshan_city_item,
			R.array.huaibei_city_item, R.array.tongling_city_item,
			R.array.anqing_city_item, R.array.huangshan_city_item,
			R.array.chuzhou_city_item, R.array.fuyang_city_item,
			R.array.anhui_suzhou_city_item, R.array.chaohu_city_item,
			R.array.luan_city_item, R.array.haozhou_city_item,
			R.array.chizhou_city_item, R.array.xuancheng_city_item };
	private int[] countyOfFuJian = { R.array.huzhou_city_item,
			R.array.xiamen_city_item, R.array.putian_city_item,
			R.array.sanming_city_item, R.array.quanzhou_city_item,
			R.array.zhangzhou_city_item, R.array.nanp_city_item,
			R.array.longyan_city_item, R.array.ningde_city_item };
	private int[] countyOfJiangXi = { R.array.nanchang_city_item,
			R.array.jingdezhen_city_item, R.array.pingxiang_city_item,
			R.array.jiujiang_city_item, R.array.xinyu_city_item,
			R.array.yingtan_city_item, R.array.ganzhou_city_item,
			R.array.jian_city_item, R.array.jiangxi_yichun_city_item,
			R.array.jiangxi_wuzhou_city_item, R.array.shangrao_city_item };
	private int[] countyOfShanDong = { R.array.jinan_city_item,
			R.array.qingdao_city_item, R.array.zaobo_city_item,
			R.array.zaozhuang_city_item, R.array.dongying_city_item,
			R.array.yantai_city_item, R.array.weifang_city_item,
			R.array.jining_city_item, R.array.taian_city_item,
			R.array.weihai_city_item, R.array.rizhao_city_item,
			R.array.laiwu_city_item, R.array.linxi_city_item,
			R.array.dezhou_city_item, R.array.liaocheng_city_item,
			R.array.shandong_bingzhou_city_item, R.array.heze_city_item };
	private int[] countyOfHeNan = { R.array.zhenshou_city_item,
			R.array.kaifang_city_item, R.array.luoyang_city_item,
			R.array.kaipingshan_city_item, R.array.anyang_city_item,
			R.array.hebi_city_item, R.array.xinxiang_city_item,
			R.array.jiaozuo_city_item, R.array.buyang_city_item,
			R.array.xuchang_city_item, R.array.leihe_city_item,
			R.array.sanmenxia_city_item, R.array.nanyang_city_item,
			R.array.shangqiu_city_item, R.array.xinyang_city_item,
			R.array.zhoukou_city_item, R.array.zhumadian_city_item };
	private int[] countyOfHuBei = { R.array.wuhan_city_item,
			R.array.huangshi_city_item, R.array.shiyan_city_item,
			R.array.yichang_city_item, R.array.xiangpan_city_item,
			R.array.erzhou_city_item, R.array.jinmen_city_item,
			R.array.xiaogan_city_item, R.array.hubei_jinzhou_city_item,
			R.array.huanggang_city_item, R.array.xianning_city_item,
			R.array.suizhou_city_item, R.array.enshi_city_item,
			R.array.shenglongjia_city_item };
	private int[] countyOfHuNan = { R.array.changsha_city_item,
			R.array.zhuzhou_city_item, R.array.xiangtan_city_item,
			R.array.hengyang_city_item, R.array.shaoyang_city_item,
			R.array.yueyang_city_item, R.array.changde_city_item,
			R.array.zhangjiajie_city_item, R.array.yiyang_city_item,
			R.array.hunan_bingzhou_city_item, R.array.yongzhou_city_item,
			R.array.huaihua_city_item, R.array.loudi_city_item,
			R.array.xiangxi_city_item };
	private int[] countyOfGuangDong = { R.array.guangzhou_city_item,
			R.array.shaoguan_city_item, R.array.shenzhen_city_item,
			R.array.zhuhai_city_item, R.array.shantou_city_item,
			R.array.foshan_city_item, R.array.jiangmen_city_item,
			R.array.zhangjiang_city_item, R.array.maoming_city_item,
			R.array.zhaoqing_city_item, R.array.huizhou_city_item,
			R.array.meizhou_city_item, R.array.shanwei_city_item,
			R.array.heyuan_city_item, R.array.yangjiang_city_item,
			R.array.qingyuan_city_item, R.array.dongguan_city_item,
			R.array.zhongshan_city_item, R.array.chaozhou_city_item,
			R.array.jiyang_city_item, R.array.yunfu_city_item };
	private int[] countyOfGuangXi = { R.array.nanning_city_item,
			R.array.liuzhou_city_item, R.array.guilin_city_item,
			R.array.guangxi_wuzhou_city_item, R.array.beihai_city_item,
			R.array.fangchenggang_city_item, R.array.qinzhou_city_item,
			R.array.guigang_city_item, R.array.yuelin_city_item,
			R.array.baise_city_item, R.array.hezhou_city_item,
			R.array.hechi_city_item, R.array.laibing_city_item,
			R.array.chuangzuo_city_item };
	private int[] countyOfHaiNan = { R.array.haikou_city_item,
			R.array.sanya_city_item };
	private int[] countyOfChongQing = { R.array.chongqing_city_item };
	private int[] countyOfSiChuan = { R.array.chengdu_city_item,
			R.array.zigong_city_item, R.array.panzhihua_city_item,
			R.array.luzhou_city_item, R.array.deyang_city_item,
			R.array.mianyang_city_item, R.array.guangyuan_city_item,
			R.array.suining_city_item, R.array.neijiang_city_item,
			R.array.leshan_city_item, R.array.nanchong_city_item,
			R.array.meishan_city_item, R.array.yibing_city_item,
			R.array.guangan_city_item, R.array.dazhou_city_item,
			R.array.yaan_city_item, R.array.bazhong_city_item,
			R.array.ziyang_city_item, R.array.abei_city_item,
			R.array.ganmu_city_item, R.array.liangshan_city_item };
	private int[] countyOfGuiZhou = { R.array.guiyang_city_item,
			R.array.lupanshui_city_item, R.array.zhunyi_city_item,
			R.array.anshun_city_item, R.array.tongren_city_item,
			R.array.qingxinan_city_item, R.array.biji_city_item,
			R.array.qingdongnan_city_item, R.array.qingnan_city_item };
	private int[] countyOfYunNan = { R.array.kunming_city_item,
			R.array.qujing_city_item, R.array.yuexi_city_item,
			R.array.baoshan_city_item, R.array.zhaotong_city_item,
			R.array.lijiang_city_item, R.array.simao_city_item,
			R.array.lingcang_city_item, R.array.chuxiong_city_item,
			R.array.honghe_city_item, R.array.wenshan_city_item,
			R.array.xishuangbanna_city_item, R.array.dali_city_item,
			R.array.dehuang_city_item, R.array.nujiang_city_item,
			R.array.diqing_city_item };
	private int[] countyOfXiZang = { R.array.lasa_city_item,
			R.array.changdu_city_item, R.array.shannan_city_item,
			R.array.rgeze_city_item, R.array.naqu_city_item,
			R.array.ali_city_item, R.array.linzhi_city_item };
	private int[] countyOfShanXi2 = { R.array.xian_city_item,
			R.array.tongchuan_city_item, R.array.baoji_city_item,
			R.array.xianyang_city_item, R.array.weinan_city_item,
			R.array.yanan_city_item, R.array.hanzhong_city_item,
			R.array.yulin_city_item, R.array.ankang_city_item,
			R.array.shangluo_city_item };
	private int[] countyOfGanSu = { R.array.lanzhou_city_item,
			R.array.jiayuguan_city_item, R.array.jinchang_city_item,
			R.array.baiyin_city_item, R.array.tianshui_city_item,
			R.array.wuwei_city_item, R.array.zhangyue_city_item,
			R.array.pingliang_city_item, R.array.jiuquan_city_item,
			R.array.qingyang_city_item, R.array.dingxi_city_item,
			R.array.longnan_city_item, R.array.linxia_city_item,
			R.array.gannan_city_item };
	private int[] countyOfQingHai = { R.array.xining_city_item,
			R.array.haidong_city_item, R.array.haibai_city_item,
			R.array.huangnan_city_item, R.array.hainan_city_item,
			R.array.guluo_city_item, R.array.yushu_city_item,
			R.array.haixi_city_item };
	private int[] countyOfNingXia = { R.array.yinchuan_city_item,
			R.array.shizuishan_city_item, R.array.wuzhong_city_item,
			R.array.guyuan_city_item, R.array.zhongwei_city_item };
	private int[] countyOfXinJiang = { R.array.wulumuqi_city_item,
			R.array.kelamayi_city_item, R.array.tulyfan_city_item,
			R.array.hami_city_item, R.array.changji_city_item,
			R.array.boertala_city_item, R.array.bayinguolen_city_item,
			R.array.akesu_city_item, R.array.kemuleisu_city_item,
			R.array.geshen_city_item, R.array.hetian_city_item,
			R.array.yili_city_item, R.array.tacheng_city_item,
			R.array.aleitai_city_item, R.array.shihezi_city_item,
			R.array.alaer_city_item, R.array.tumushihe_city_item,
			R.array.wujiaqu_city_item };
	private int[] countyOfHongKong = {};
	private int[] countyOfAoMen = {};
	private int[] countyOfTaiWan = {};
	private ArrayAdapter<CharSequence> province_adapter;
	private ArrayAdapter<CharSequence> city_adapter;
	private ArrayAdapter<CharSequence> county_adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.my_userinfo_edit, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		user = SerUser.deSerializationUser((String) SharedPreferencesUtils
				.getParam("SerUser", getActivity(), "user", ""));
		TextView tv_title = (TextView)view.findViewById(R.id.top_text);
		tv_title.setText(getResources().getString(R.string.my_edit));
		Button back = (Button)view.findViewById(R.id.top_left);
		back.setOnClickListener(this);
		Button right = (Button)view.findViewById(R.id.top_right);
		right.setVisibility(View.GONE);
		
		layout_icon  = (RelativeLayout)view.findViewById(R.id.my_edit_icon);
		layout_name = (RelativeLayout)view.findViewById(R.id.my_edit_name);
		layout_Signature = (RelativeLayout)view.findViewById(R.id.my_edit_Signature);
		layout_address = (RelativeLayout)view.findViewById(R.id.my_edit_address);
		layout_sex = (RelativeLayout)view.findViewById(R.id.my_edit_sex);
		layout_icon.setOnClickListener(this);
		layout_name.setOnClickListener(this);
		layout_Signature.setOnClickListener(this);
		layout_address.setOnClickListener(this);
		layout_sex.setOnClickListener(this);
		
		iv_Icon = (ImageView)view.findViewById(R.id.my_edit_icon_image);
		
		tv_Name = (TextView)view.findViewById(R.id.my_edit_name_right);
		tv_Signature = (TextView)view.findViewById(R.id.my_edit_Signature_right);
		tv_sex = (TextView)view.findViewById(R.id.my_edit_sex_right);
		tv_address = (TextView)view.findViewById(R.id.my_edit_address_right);
		
		tv_Name.setText(user.getName());
		tv_Signature.setText(user.getDescription());
		tv_sex.setText(user.judgeGender());
		tv_address.setText(user.getLocation());
		MaimobApplication.imageLoader.displayImage(StringUtils.checkImgPath(user.getIcon()), iv_Icon, ImageUtil.getOption());
		
		adapter_Pic = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, items);
		builder_Pic = new AlertDialog.Builder(getActivity());
		builder_Pic.setAdapter(adapter_Pic, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				DialogChose(which);
			}
		});
		dialog = builder_Pic.create();
		
		iv_Icon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
		initAddressPop();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}
	
	private void DialogChose(int which){
		if (which == 0) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			File file = new File(Environment.getExternalStorageDirectory() + "/Mai",
					"Mai_"
					+String.valueOf(System.currentTimeMillis()) + ".jpg");
			mImageCaptureUri = Uri.fromFile(file);
			intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
					mImageCaptureUri);
			intent.putExtra("return-data", true);
			startActivityForResult(intent, PICK_FROM_CAMERA);
		}else if (which == 1) {
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(Intent.createChooser(intent, "XWKKX"), PICK_FROM_FILE);
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) {
			MyLogger.jLog().i("result  " + resultCode + "  request  " + requestCode);
			return;
		}
		if (requestCode == PICK_FROM_FILE) {
			Uri uri = data.getData();
			startPhotoZoom(uri, 150);
		}else if (requestCode == PICK_FROM_CAMERA) {
			startPhotoZoom(mImageCaptureUri, 150);
		}else if (requestCode == PHOTO_REQUEST_CUT) {
			setPicToView(data);
			mFragmentManage.Refresh_userInfo = true;
		}
	}
	
	 //将进行剪裁后的图片显示到UI界面上
	private void setPicToView(Intent picdata) {
		Bundle bundle = picdata.getExtras();
		if (bundle != null) {
			Bitmap photo = bundle.getParcelable("data");
			File filePath = bitmapToFile(photo);
			MaimobApplication.imageLoader.displayImage(
					StringUtils.checkImgPath(filePath.toString()), iv_Icon,
					ImageUtil.getOption());
			SharedPreferencesUtils.setParam(SharedPreferencesUtils.user,
					getActivity(), SharedPreferencesUtils.user_icon,
					StringUtils.checkImgPath(filePath.toString()));
			user.setIcon(StringUtils.checkImgPath(filePath.toString()));
			user.saveUser(getActivity(), SerUser.serializeUser(user));
		}

	}
    
	private File bitmapToFile(Bitmap bitmap) {
		File file = new File(
				Environment.getExternalStorageDirectory() + "/Mai", "Mai_crop_"
						+ String.valueOf(System.currentTimeMillis()) + ".jpg");
		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
			bos.flush();
			bos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}
	
	
    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		final EditText et_input  = new EditText(getActivity());
		switch (v.getId()) {
		case R.id.pop_address_cancle:
			pop.dismiss();
		case R.id.pop_address_queding:
			pop.dismiss();
			String location = mProvince + "-" + mCity +"-" + mCounty;
			tv_address.setText(location);
			mFragmentManage.Refresh_userInfo = true;
			user.setLocation(location);
			user.saveUser(getActivity(), SerUser.serializeUser(user));
			break;
		case R.id.top_left:
			mFragmentManage.BackStatck(getActivity());
			//TODO REFRESH
			break;

		case R.id.my_edit_icon:
			dialog.show();
			break;
		case R.id.my_edit_name:
			et_input.setHint(user.getName());
			new AlertDialog.Builder(getActivity()).setView(et_input).setTitle("编辑昵称").setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			})
			.setPositiveButton("确认", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String content = et_input.getText().toString();
//					SharedPreferencesUtils.setParam(SharedPreferencesUtils.user, getActivity(), SharedPreferencesUtils.user_name, content);
					user.setName(content);
					tv_Name.setText(content);
					dialog.dismiss();
					mFragmentManage.Refresh_userInfo = true;
					user.saveUser(getActivity(), SerUser.serializeUser(user));
					MyLogger.jLog().i(content);
				}
			})
			.show();
			break;
		case R.id.my_edit_Signature:
			et_input.setHint(user.getDescription());
			new AlertDialog.Builder(getActivity()).setView(et_input).setTitle("编辑签名").setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			})
			.setPositiveButton("确认", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String content = et_input.getText().toString();
					user.setDescription(content);
					tv_Signature.setText(content);
					dialog.dismiss();
					mFragmentManage.Refresh_userInfo = true;
					user.saveUser(getActivity(), SerUser.serializeUser(user));
					MyLogger.jLog().i(content);
				}
			})
			.show();
			break;
		case R.id.my_edit_address:
//			et_input.setHint(user.getLocation());
//			new AlertDialog.Builder(getActivity()).setView(et_input).setTitle("编辑地址").setNegativeButton("取消", new DialogInterface.OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					// TODO Auto-generated method stub
//					dialog.dismiss();
//				}
//			})
//			.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					// TODO Auto-generated method stub
//					String content = et_input.getText().toString();
//					user.setLocation(content);
//					tv_address.setText(content);
//					dialog.dismiss();
//					mFragmentManage.Refresh_userInfo = true;
//					user.saveUser(getActivity(), SerUser.serializeUser(user));
//					MyLogger.jLog().i(content);
//				}
//			})
//			.show();
			pop.showAtLocation(v, Gravity.CENTER, 0, 0);
			break;
		case R.id.my_edit_sex:
			final String[] gender = new String[]{"女","男"};
			new AlertDialog.Builder(getActivity()).setTitle("编辑性别").setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			})
			.setPositiveButton("确认", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					logger.i("index  " +gender[selectIndex]);
					user.saveUser(getActivity(), SerUser.serializeUser(user));
					tv_sex.setText(user.judgeGender());
					mFragmentManage.Refresh_userInfo = true;
				}
			})
			.setSingleChoiceItems(gender, user.getGender(), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					user.setGender(which);;
				}
			})
			.show();
			break;
		}
	}
	
	private void initAddressPop(){
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View popView = inflater.inflate(R.layout.pop_address, null);
		tv_address_result = (TextView)popView.findViewById(R.id.pop_address_text);
		bt_confirm = (Button)popView.findViewById(R.id.pop_address_queding);
		bt_cancel =(Button)popView.findViewById(R.id.pop_address_cancle);
		bt_cancel.setOnClickListener(this);
		bt_confirm.setOnClickListener(this);
		loadSpinner(popView);
		pop = new PopupWindow(popView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
	}
	
	private void loadSpinner(final View popView) {

		province_spinner = (Spinner) popView.findViewById(R.id.province_spinner);
		province_spinner.setPrompt("请选择省份");
		province_adapter = ArrayAdapter.createFromResource(getActivity(),
				R.array.province_item, android.R.layout.simple_spinner_item);
		province_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		province_spinner.setAdapter(province_adapter);
		// select(province_spinner, province_adapter, R.array.province_item);
		province_spinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						provinceId = province_spinner.getSelectedItemPosition();
						province_spinner.getSelectedItem().toString();
						city_spinner = (Spinner) popView.findViewById(R.id.city_spinner);
						if (true) {
							Log.v("test", "province: "
									+ province_spinner.getSelectedItem()
											.toString() + provinceId.toString());
							mProvince = province_spinner.getSelectedItem()
									.toString();
							county_spinner = (Spinner) popView.findViewById(R.id.county_spinner);
							city_spinner = (Spinner) popView.findViewById(R.id.city_spinner);
							city_spinner.setPrompt("请选择城市");
							select(city_spinner, city_adapter, city[provinceId]);
							city_spinner
									.setOnItemSelectedListener(new OnItemSelectedListener() {

										@Override
										public void onItemSelected(
												AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
											cityId = city_spinner
													.getSelectedItemPosition();
											city_spinner.getSelectedItem()
													.toString();
											mCity = city_spinner
													.getSelectedItem()
													.toString();
											Log.v("test", "city: "
													+ city_spinner
															.getSelectedItem()
															.toString()
													+ cityId.toString());
											if (true) {
												county_spinner = (Spinner) popView.findViewById(R.id.county_spinner);
												county_spinner
														.setPrompt("请选择县区");
												switch (provinceId) {
												case 0:
													select(county_spinner,
															county_adapter,
															countyOfBeiJing[cityId]);
													break;
												case 1:
													select(county_spinner,
															county_adapter,
															countyOfShangHai[cityId]);
													break;
												case 2:
													select(county_spinner,
															county_adapter,
															countyOfGuangDong[cityId]);
													break;
												case 3:
													select(county_spinner,
															county_adapter,
															countyOfShanXi1[cityId]);
													break;
												case 4:
													select(county_spinner,
															county_adapter,
															countyOfNeiMengGu[cityId]);
													break;
												case 5:
													select(county_spinner,
															county_adapter,
															countyOfLiaoNing[cityId]);
													break;
												case 6:
													select(county_spinner,
															county_adapter,
															countyOfJiLin[cityId]);
													break;
												case 7:
													select(county_spinner,
															county_adapter,
															countyOfHeiLongJiang[cityId]);
													break;
												case 8:
													select(county_spinner,
															county_adapter,
															countyOfTianJing[cityId]);
													break;
												case 9:
													select(county_spinner,
															county_adapter,
															countyOfJiangSu[cityId]);
													break;
												case 10:
													select(county_spinner,
															county_adapter,
															countyOfZheJiang[cityId]);
													break;
												case 11:
													select(county_spinner,
															county_adapter,
															countyOfAnHui[cityId]);
													break;
												case 12:
													select(county_spinner,
															county_adapter,
															countyOfFuJian[cityId]);
													break;
												case 13:
													select(county_spinner,
															county_adapter,
															countyOfJiangXi[cityId]);
													break;
												case 14:
													select(county_spinner,
															county_adapter,
															countyOfShanDong[cityId]);
													break;
												case 15:
													select(county_spinner,
															county_adapter,
															countyOfHeNan[cityId]);
													break;
												case 16:
													select(county_spinner,
															county_adapter,
															countyOfHuBei[cityId]);
													break;
												case 17:
													select(county_spinner,
															county_adapter,
															countyOfHuNan[cityId]);
													break;
												case 18:
													select(county_spinner,
															county_adapter,
															countyOfHeBei[cityId]);
													break;
												case 19:
													select(county_spinner,
															county_adapter,
															countyOfGuangXi[cityId]);
													break;
												case 20:
													select(county_spinner,
															county_adapter,
															countyOfHaiNan[cityId]);
													break;
												case 21:
													select(county_spinner,
															county_adapter,
															countyOfChongQing[cityId]);
													break;
												case 22:
													select(county_spinner,
															county_adapter,
															countyOfSiChuan[cityId]);
													break;
												case 23:
													select(county_spinner,
															county_adapter,
															countyOfGuiZhou[cityId]);
													break;
												case 24:
													select(county_spinner,
															county_adapter,
															countyOfYunNan[cityId]);
													break;
												case 25:
													select(county_spinner,
															county_adapter,
															countyOfXiZang[cityId]);
													break;
												case 26:
													select(county_spinner,
															county_adapter,
															countyOfShanXi2[cityId]);
													break;
												case 27:
													select(county_spinner,
															county_adapter,
															countyOfGanSu[cityId]);
													break;
												case 28:
													select(county_spinner,
															county_adapter,
															countyOfQingHai[cityId]);
													break;
												case 29:
													select(county_spinner,
															county_adapter,
															countyOfNingXia[cityId]);
													break;
												case 30:
													select(county_spinner,
															county_adapter,
															countyOfXinJiang[cityId]);
													break;
												case 31:
													select(county_spinner,
															county_adapter,
															countyOfHongKong[cityId]);
													break;
												case 32:
													select(county_spinner,
															county_adapter,
															countyOfAoMen[cityId]);
													break;
												case 33:
													select(county_spinner,
															county_adapter,
															countyOfTaiWan[cityId]);
													break;

												default:
													break;
												}

												county_spinner
														.setOnItemSelectedListener(new OnItemSelectedListener() {

															@Override
															public void onItemSelected(
																	AdapterView<?> arg0,
																	View arg1,
																	int arg2,
																	long arg3) {
																county_spinner
																		.getSelectedItem()
																		.toString();
																mCounty = county_spinner
																		.getSelectedItem()
																		.toString();
																MyLogger.jLog().i("province " + mProvince +"  city  " + mCity + " mcounty  " +mCounty);
																Log.i("FFF", "/// "  );
																tv_address_result.setText(mProvince + "-" + mCity +"-" + mCounty);
															}

															@Override
															public void onNothingSelected(
																	AdapterView<?> arg0) {

															}

														});
											}
										}

										@Override
										public void onNothingSelected(
												AdapterView<?> arg0) {
											// TODO Auto-generated method stub

										}

									});
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
	}
	
	private void select(Spinner spin, ArrayAdapter<CharSequence> adapter,
			int arry) {
		adapter = ArrayAdapter.createFromResource(getActivity(), arry,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(adapter);
		// spin.setSelection(0,true);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (needRefresh) {
			mFragmentManage.RefreshFrag(getActivity(), mFragmentManage.Tag_Userinfo);
		}
	}
	
}
