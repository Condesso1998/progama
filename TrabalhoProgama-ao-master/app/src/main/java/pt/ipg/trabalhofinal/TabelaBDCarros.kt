package pt.ipg.trabalhofinal

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns
import pt.ipg.livros.TabelaBD

class TabelaBDCarros (db: SQLiteDatabase) : TabelaBD(db, MATRICULA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $MATRICULA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $MATRICULA TEXT NOT NULL, $MARCA TEXT NOT NULL, $MODELO TEXT NOT NULL, $COR TEXT NOT NULL,FOREIGN KEY ($CAMPO_CLIENTE_ID) REFERENCES ${TabelaBDCliente.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)  ")

    }
      override fun query(
          columns: Array<String>,
          selection: String,
          selectionArgs: Array<String>,
          groupBy: String?,
          having: String?,
          orderBy: String?
      ): Cursor? {
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = "$MATRICULA INNER JOIN ${TabelaBDCliente.NOME} ON ${TabelaBDCliente.CAMPO_ID} = $CAMPO_CLIENTE_ID"

        return queryBuilder.query(db, columns, selection, selectionArgs, groupBy, having, orderBy)
    }


    companion object{

        const val MATRICULA = "matricula"
        const val CAMPO_ID ="${TabelaBDCliente.NOME}.${TabelaBDCliente.NOME}{BaseColumns._ID}"
        const val MARCA = "marca"
        const val MODELO = "modelo"
        const val COR = "cor"
        const val CAMPO_CLIENTE_ID = "categoriaId"
        val TODAS_COLUNAS = arrayOf(CAMPO_ID, TabelaBDCliente.NOME)
    }



}

