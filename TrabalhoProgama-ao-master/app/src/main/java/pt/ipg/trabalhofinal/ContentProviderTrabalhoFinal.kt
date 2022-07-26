package pt.ipg.trabalhofinal

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class ContentProviderTrabalhoFinal: ContentProvider() {
    var dbOpenHelper: BDStandOpenHelper? = null


    override fun onCreate(): Boolean {
        dbOpenHelper = BDStandOpenHelper(context)

        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val db = dbOpenHelper!!.readableDatabase

        requireNotNull(projection)
        val colunas = projection as Array<String>

        val argsSeleccao = selectionArgs as Array<String>?

        val id = uri.lastPathSegment

        val cursor = when (getUriMatcher().match(uri)) {
            URI_Carros -> TabelaBDCarros(db).query(colunas, "${BaseColumns._ID}=?", arrayOf("${id}"), null, null, null)
            URI_Clientes -> TabelaBDCliente(db).query (colunas, "${BaseColumns._ID}=?", arrayOf("${id}"), null, null, null)
            URI_Carros_ESPECIFICO -> TabelaBDCarros(db).query(colunas, "${BaseColumns._ID}=?", arrayOf("${id}"), null, null, null)
            URI_Cliente_ESPECIFICO -> TabelaBDCliente(db).query(colunas, "${BaseColumns._ID}=?", arrayOf("${id}"), null, null, null)
            else -> null
        }

        return cursor
    }

    override fun getType(uri: Uri): String? {
        return when (getUriMatcher().match(uri)) {
            URI_Carros -> "$MULTIPLOS_REGISTOS/${TabelaBDCarros.MATRICULA}"
            URI_Clientes -> "$MULTIPLOS_REGISTOS/${TabelaBDCliente.NOME}"
            URI_Carros_ESPECIFICO -> "$UNICO_REGISTO/${TabelaBDCarros.MATRICULA}"
            URI_Carros_ESPECIFICO -> "$UNICO_REGISTO/${TabelaBDCliente.NOME}"
            else -> null
        }


    }
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbOpenHelper!!.writableDatabase

        requireNotNull(values)

        val id = when (getUriMatcher().match(uri)) {
            URI_Carros -> TabelaBDCarros(db).insert(values)
            URI_Cliente_ESPECIFICO -> TabelaBDCliente(db).insert(values)
            else -> -1
        }

        db.close()

        if (id == -1L) return null

        return Uri.withAppendedPath(uri, "$id")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbOpenHelper!!.writableDatabase


        val id = uri.lastPathSegment

        val registosApagados = when (getUriMatcher().match(uri)) {
            URI_Carros_ESPECIFICO -> TabelaBDCarros(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_Cliente_ESPECIFICO -> TabelaBDCliente(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
            else -> 0
        }

        db.close()

        return registosApagados
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {

            requireNotNull(values)

            val db = dbOpenHelper!!.writableDatabase

            val id = uri.lastPathSegment

            val registosAlterados = when (getUriMatcher().match(uri)) {
                URI_Carros_ESPECIFICO -> TabelaBDCarros(db).update(values, "${BaseColumns._ID}=?", arrayOf("${id}"))
                URI_Cliente_ESPECIFICO -> TabelaBDCliente(db).update(values,"${BaseColumns._ID}=?", arrayOf("${id}"))
                else -> 0
            }

            db.close()

            return registosAlterados
        }
    companion object {
        private val AUTHORITY = "pt.ipg.trabalhofinal"

        private val URI_Clientes  = 100
        private val URI_Cliente_ESPECIFICO = 101
        private val URI_Carros = 200
        private val URI_Carros_ESPECIFICO  = 201

        private val UNICO_REGISTO = "vnd.android.cursor.item"
        private val MULTIPLOS_REGISTOS = "vnd.android.cursor.dir"

        private val ENDERECO_BASE = Uri.parse("content://$AUTHORITY")
        val ENDERECO_Carros = Uri.withAppendedPath(ENDERECO_BASE, TabelaBDCarros.MATRICULA)
        val ENDERECO_Clientes = Uri.withAppendedPath(ENDERECO_BASE, TabelaBDCliente.NOME)



        fun getUriMatcher() : UriMatcher {
            var uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            uriMatcher.addURI(AUTHORITY, TabelaBDCliente.NOME, URI_Clientes)
            uriMatcher.addURI(AUTHORITY, "${TabelaBDCliente.NOME}/#", URI_Cliente_ESPECIFICO)
            uriMatcher.addURI(AUTHORITY, TabelaBDCarros.MATRICULA, URI_Carros)
            uriMatcher.addURI(AUTHORITY, "${TabelaBDCarros.MATRICULA}/#", URI_Carros_ESPECIFICO)

            return uriMatcher
        }
    }
    }




