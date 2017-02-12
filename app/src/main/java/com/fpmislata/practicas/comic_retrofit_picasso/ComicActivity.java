package com.fpmislata.practicas.comic_retrofit_picasso;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fpmislata.practicas.comic_retrofit_picasso.model.ComicModel;
import com.fpmislata.practicas.comic_retrofit_picasso.presenter.ComicPresenter;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class ComicActivity extends AppCompatActivity implements ComicPresenter.ComicPresenterListener{

    ComicPresenter comicPresenter;
    ImageView imagen;
    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic);


        imagen = (ImageView) findViewById(R.id.imageView);
        bar = (ProgressBar) findViewById(R.id.progressBar);

        comicPresenter = new ComicPresenter(ComicActivity.this, this);
        comicPresenter.getComic("");

        //al pulsar la imagen carga una nueva de forma aleatoria
        imagen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Random random = new Random();
                String intRandom = String.valueOf(random.nextInt(1000));
                comicPresenter.getComic(intRandom);
            }
        });
    }

    @Override
    public void comicReady(ComicModel comic) {

        String url_imagen = comic.getImg();

        Picasso.with(ComicActivity.this)
                .load(url_imagen)
                //      .fetch()
                .error(R.drawable.error_image)
                .placeholder(R.drawable.progress_animation)
                // .resize(100,100)  se sale de la pantalla, no me sirve
                // .fit()  aquí el problema es que si la imagen es alargada (de varias viñetas) las comprime y se ve mal
                .into(imagen, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        bar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(ComicActivity.this, "Error al cargarse la imagen", Toast.LENGTH_LONG).show();
                        //también se mostraria la imagen de error  ( .error(R.drawable.error_image))
                    }
                });

        // imprime el json
        // Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // Log.d("main", gson.toJson(comic));

    }

}