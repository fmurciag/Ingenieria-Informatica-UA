public class ServicioStub extends Servicio{
    float result;
    public void setResult(float tipo) {
        this.result = tipo;
    }
    @Override
    public float consultaPrecio(TipoCoche tipo) {
        return result;
    }
}
