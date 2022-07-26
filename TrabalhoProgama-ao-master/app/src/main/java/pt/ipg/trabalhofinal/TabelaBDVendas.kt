package pt.ipg.trabalhofinal

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import pt.ipg.livros.TabelaBD


class TabelaBDVendas ( db: SQLiteDatabase) : TabelaBD(db, TabelaBDCliente.NOME) {

    override fun cria(){
        db.execSQL("CREATE TABLE $Nvenda(${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $DataVenda St NOT NULL, $CAMPO_NOME TEXT NOT NULL)")

    }

    companion object {
        const val Nvenda=" numeroDAvenda"
        const val DataVenda="data da venda"
        const val CAMPO_NOME ="nome"
    }
}