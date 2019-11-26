

/*
 * Created by
 * Copyright Ⓒ 2019 . All rights reserved.
 */

package com.rogergcc.sisfaedcoh.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.rogergcc.sisfaedcoh.R;
import com.rogergcc.sisfaedcoh.model.Accesorio;
import com.rogergcc.sisfaedcoh.utils.ClipBoardManager;

import java.util.ArrayList;

import static com.rogergcc.sisfaedcoh.fragment.AccesoriosListFragment.activityFragmentProduct;


public class AccesoriosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Accesorio> productArrayList;
    private static final int PRODUCT_ITEM_VIEW_TYPE = 0 ;

    public AccesoriosAdapter(Context context, ArrayList<Accesorio> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case PRODUCT_ITEM_VIEW_TYPE :
            default:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_barcode,parent,false);
                return new AccesorioViewHolder(view);
           
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       int viewType = getItemViewType(position);
       switch (viewType){
           case PRODUCT_ITEM_VIEW_TYPE:
           default :
               AccesorioViewHolder AccesorioViewHolder = (AccesorioViewHolder)holder;
               setProductView(AccesorioViewHolder , position);
               break ;
          

       }
    }

    private void setProductView(AccesorioViewHolder holder, final  int position) {
        final Accesorio product = productArrayList.get(position);
        holder.txtScanResult.setText("Marca: \n" + product.getMarca());
        holder.txtScanTime.setText("Serie: \n" +product.getSerie());
        holder.txtScanNo.setText("codigo: "+ product.getCodigo());

        if(position%2==0){
            holder.layoutRightButtons.setBackgroundColor(context.getResources().getColor(R.color.card_right_blue));
        }
        if(position%3==0){
            holder.layoutRightButtons.setBackgroundColor(context.getResources().getColor(R.color.card_right_purple));
        }

//        Glide.with(context).load(URL+"imagenes/camarasony.jpg").into(imgPoster);

//        holder.layoutSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, WebViewActivity.class);
//                intent.putExtra("product_id",product.getProductBarcodeNo());
//                context.startActivity(intent);
//            }
//        });


        holder.cardViewCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TTFancyGifDialog.Builder(activityFragmentProduct)
                        .setTitle("Modelo: "+ product.getModelo())
                        .setMessage("Denominacion \n"+product.getDenominacion())
                        .setPositiveBtnText("Ok")
                        .setPositiveBtnBackground("#22b573")
                        .setGifResource(R.drawable.scan_stock)      //pass your gif, png or jpg
                        .isCancellable(true)
                        .OnPositiveClicked(new TTFancyGifDialogListener() {
                            @Override
                            public void OnClick() {
//                                Toast.makeText(MainActivity.this,"Ok",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build();
            }
        });

        holder.layoutCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipBoardManager clipBoardManager = new ClipBoardManager();
                clipBoardManager.copyToClipboard(context,product.getCodigo());
                Snackbar.make(v,"Copiado",Snackbar.LENGTH_SHORT).show();
            }
        });
       

    }

    



    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class AccesorioViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutRightButtons ;
        private CardView cardViewCodigo ;
        private RelativeLayout layoutCopy , layoutSearch ;
        private TextView txtScanResult , txtScanNo , txtScanTime ;
        private Button btnShare ;

        public AccesorioViewHolder(View itemView) {
            super(itemView);
            cardViewCodigo = itemView.findViewById(R.id.cardViewCodigo);

            layoutRightButtons = itemView.findViewById(R.id.layout_right_buttons);
            layoutCopy = itemView.findViewById(R.id.layout_copy);
            layoutSearch = itemView.findViewById(R.id.layout_search);
            txtScanNo = itemView.findViewById(R.id.txt_scan_no);
            txtScanResult = itemView.findViewById(R.id.txt_scan_result);
            txtScanTime = itemView.findViewById(R.id.txt_date_time);
            btnShare = itemView.findViewById(R.id.btn_share);

        }
    }


}
