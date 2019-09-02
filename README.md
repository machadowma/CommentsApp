# CommentsApp
Exemplo de App Android que implementa ListView personalizada.
O app possui duas telas:
 * Adicionar comentário: Formulário para escrever um comentário e atribuir uma nota entre 0 e 5.
 * Listar comentários: Todos os comentários são exibidos em uma ListView personalizada.

<table>
<tr align=center>
<td><img src="https://github.com/machadowma/CommentsApp/blob/master/add.png" align="left" height="360" width="180" ></td>
<td><img src="https://github.com/machadowma/CommentsApp/blob/master/main.png" align="left" height="360" width="180" ></td>
</tr>
<tr align=center>
<td>Adicionar comentário</td>
<td>Exibir comentários</td>
</tr>
</table>

# Criando ListView personalizada

As etapas a seguir são usadas para implementar a ListView personalizada:

1. O primeiro passo para criar uma lista personalizada é identificar o modelo de dados para cada linha. No nosso exemplo, exibiremos a lista de objetos Comment.
2. Em segundo lugar, vamos declarar a ListView no layout da Activity.
3. Em seguida, vamos declarar o layout para cada item de linha.
4. Será necessário criar uma classe de adaptador personalizada estendendo a classe BaseAdapter.
5. Por fim, vamos instanciar o adaptador personalizado e definer o ListView chamando o método setAdapter ().

## 1 - Declarando o modelo de dados
O objeto Comment representará cada linha da lista. Declare a classe Comment.java e adicione os seguintes trechos de código.

```
public class Comment {
    private Integer id;
    private String comment;
    private Integer rating;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}

```

## 2. Declarando a ListView
A tela inicial deve ser uma BasicActivity com uma ListView.
```
 . . .

    <ListView
        android:id="@+id/listView"
        android:layout_width="0dp"
        android:layout_height="0dp"
        android:layout_marginBottom="60dp"
        android:layout_marginEnd="8dp"
        android:layout_marginLeft="8dp"
        android:layout_marginRight="8dp"
        android:layout_marginStart="8dp"
        android:layout_marginTop="8dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
        
 . . .
```

## 3. Criando o layout de cada linha
Vamos criar um novo layout personalizado para o item de linha da exibição em lista que contém a nota, o comentário e um emoticon.

list_row_layout.xml
```
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">


    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="horizontal">

            <TextView
                android:id="@+id/textViewNota"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:layout_margin="8dp"
                android:text="TextView"
                android:textSize="24sp"
                android:textStyle="bold" />

            <ImageView
                android:id="@+id/imageViewRating"
                android:layout_width="100dp"
                android:layout_height="50dp"
                android:layout_margin="8dp"
                android:layout_weight="1"
                android:scaleType="fitCenter"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintTop_toTopOf="parent"
                app:srcCompat="@drawable/bom" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="horizontal">

            <TextView
                android:id="@+id/textViewComment"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_margin="8dp"
                android:layout_weight="1"
                android:text="TextView"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent" />
        </LinearLayout>

    </LinearLayout>

</android.support.constraint.ConstraintLayout>
```
O emoticon de cada linha será escolhido de acordo com a nota do comentário.

<table>
<tr align=center>
<td><img src="https://github.com/machadowma/CommentsApp/blob/master/ruim.png" align="left" height="100" width="100" ></td>
<td><img src="https://github.com/machadowma/CommentsApp/blob/master/medio.png" align="left" height="100" width="100" ></td>
<td><img src="https://github.com/machadowma/CommentsApp/blob/master/bom.png" align="left" height="100" width="100" ></td>
</tr>
<tr align=center>
<td>Nota entre 0 e 1</td>
<td>Nota entre 2 e 3</td>
<td>Nota entre 4 e 5</td>
</tr>
</table>


## 4. Criando um adaptador
Vamos criar uma nova classe chamada CustomListAdapter.java e estendê-la a partir de BaseAdapter. Você deve substituir os seguintes métodos da classe BaseAdpter:
* getCount(): Este método retorna o número total de contagens de linhas para a exibição em lista. Normalmente, isso contém o tamanho da lista que você passa como entrada.
* getItem(): Retorna um objeto que representa dados para cada linha.
* getItemId(): Retorna o ID inteiro exclusivo que representa cada item de linha. Vamos retornar o valor da posição inteira.
* getView(): O método getView () retorna uma instância de exibição que representa uma única linha no item ListView. Aqui você pode aumentar seu próprio layout e atualizar valores na linha da lista.

```
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {
    private ArrayList<Comment> listData;
    private LayoutInflater layoutInflater;

    static class ViewHolder {
        TextView textViewComment,textViewNota;
        ImageView imageViewRating;
    }

    public CustomListAdapter(Context aContext, ArrayList<Comment> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final int pos = position;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_row_layout, null);
            holder = new ViewHolder();
            holder.textViewComment = (TextView) convertView.findViewById(R.id.textViewComment);
            holder.imageViewRating = (ImageView) convertView.findViewById(R.id.imageViewRating);
            holder.textViewNota = (TextView) convertView.findViewById(R.id.textViewNota);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textViewComment.setText(listData.get(position).getComment());
        holder.textViewNota.setText("Nota: "+listData.get(position).getRating());
        switch (listData.get(position).getRating()){
            case 0:
                holder.imageViewRating.setImageResource(R.drawable.ruim);
                break;
            case 1:
                holder.imageViewRating.setImageResource(R.drawable.ruim);
                break;
            case 2:
                holder.imageViewRating.setImageResource(R.drawable.medio);
                break;
            case 3:
                holder.imageViewRating.setImageResource(R.drawable.medio);
                break;
            case 4:
                holder.imageViewRating.setImageResource(R.drawable.bom);
                break;
            case 5:
                holder.imageViewRating.setImageResource(R.drawable.bom);
                break;
            default:
                break;

        }

        return convertView;
    }
    
}
```

## 5. Instanciando o adaptador a associando à ListView
Vamos juntar todos eles e conectá-lo à Activity. O trecho de código a seguir descreve a classe  MainActivity.java, onde inicializamos o Adapter e o conectamos à ListView.

```
. . .
            Cursor cursor = bancoDados.rawQuery("SELECT id,comment,rating FROM comment", null);
            ArrayList<Comment> commentsArray = new ArrayList<Comment>();
            CustomListAdapter customListAdapter = new CustomListAdapter(this, commentsArray);
            if(cursor.moveToFirst()) {
                do {
                    Comment comment = new Comment();
                    comment.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    comment.setComment(cursor.getString(cursor.getColumnIndex("comment")));
                    comment.setRating(cursor.getInt(cursor.getColumnIndex("rating")));
                    commentsArray.add(comment);
                } while (cursor.moveToNext());
            }
            listView.setAdapter(customListAdapter);
. . . 
```



# License
MIT License

Copyright (c) 2019 machadowma

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
