package pt.ipg.trabalhofinal

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import pt.ipg.livros.TabelaBD


class TabelaBDCliente ( db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria(){
        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} TEXT PRIMARY KEY AUTOINCREMENT, $CAMPO_NIF INTEGER NOT NULL $CAMPO_TELEFONE INTEGER NOT NULL ON DELETE RESTRICT) ")

    }

    companion object{
        const val NOME ="nome"
        const val CAMPO_ID ="$NOME.$NOME{BaseColumns._ID}"
        const val CAMPO_NIF = "nif"
        const val CAMPO_TELEFONE = "telefone"

        val TODAS_COLUNAS = arrayOf(CAMPO_ID, NOME)
    }

}