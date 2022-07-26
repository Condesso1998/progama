package pt.ipg.trabalhofinal


import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BaseDadosTest {


    private fun appContext() =
        InstrumentationRegistry.getInstrumentation().targetContext

    private fun getWritableDatabase(): SQLiteDatabase {
        val openHelper = BDStandOpenHelper(appContext())
        return openHelper.writableDatabase
    }

    private fun insereCliente(db: SQLiteDatabase, cliente: Cliente) {
        cliente.id = TabelaBDCliente(db).insert(cliente.toContentValues())
        assertNotEquals(-1, cliente.id)
    }

    private fun insereCarro(db: SQLiteDatabase, carro: Carro) {
        carro.id = TabelaBDCarros(db).insert(carro.toContentValues())
        assertNotEquals(-1, carro.id)
    }

    @Before
    fun apagaBaseDados() {
        appContext().deleteDatabase(BDStandOpenHelper.NOME)
    }

    @Test
    fun consegueAbrirBaseDados() {
        val openHelper = BDStandOpenHelper(appContext())
        val db = openHelper.readableDatabase

        assertTrue(db.isOpen)

        db.close()
    }

    @Test
    fun consegueInserirCliente() {
        val db = getWritableDatabase()

        insereCliente(db, Cliente("Rui Condesso "))

        db.close()
    }


    fun consegueInserirCarro() {
        val db = getWritableDatabase()

        val cliente = Cliente("Rui Condesso")
        insereCliente(db, cliente )

        val Carro = Carro("AF-15-OU", "Mercedes", "A45","Cinza",cliente)
        insereCarro(db, Carro);

        db.close()
    }



    @Test
    fun consegueAlterarCliente() {
        val db = getWritableDatabase()

        val cliente = Cliente("Teste")
        insereCliente(db, cliente)

        cliente.nome = "Rui Pedro"

        val registosAlterados = TabelaBDCliente(db).update(
            cliente.toContentValues(),
            "${TabelaBDCliente.CAMPO_ID}=?",
            arrayOf("${cliente.id}"))

        assertEquals(1, registosAlterados)

        db.close()
    }
    @Test
    fun consegueEliminarCliente() {
        val db = getWritableDatabase()

        val Cliente = Cliente("Rui Condess")
        insereCliente(db, Cliente)

        val registosEliminados = TabelaBDCliente(db).delete(
            "${BaseColumns._ID}=?",
            arrayOf("${Cliente.id}"))

        assertEquals(1, registosEliminados)

        db.close()
    }
    @Test
    fun consegueEliminarCarro() {
        val db = getWritableDatabase()


        val cliente = Cliente("Rui Condesso")
        insereCliente(db, cliente )

        val Carro = Carro("AF-15-OU", "Mercedes", "A45","Cinza",cliente)
        insereCarro(db, Carro)

        val registosEliminados = TabelaBDCarros(db).delete(
            "${BaseColumns._ID}=?",
            arrayOf("${Carro.id}"))

        assertEquals(1, registosEliminados)

        db.close()
    }
    @Test
    fun consegueLerCliente() {
        val db = getWritableDatabase()

        val cliente = Cliente("Rui Condesso")
        insereCliente(db, cliente  )

        val cursor = TabelaBDCliente(db).query(
            TabelaBDCliente.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf("${cliente.id}"),
            null,
            null,
            null
        )

        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val clienteBD = Cliente.fromCursor(cursor)
// eu sou gay
        assertEquals(cliente, clienteBD)

        db.close()
    }



}