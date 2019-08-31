import 'package:flutter/material.dart';
import 'package:validators/validators.dart';

void main() {
  runApp(MaterialApp(
    home: Home(),
    debugShowCheckedModeBanner: false,
  ));
}

class Home extends StatefulWidget {
  @override
  _HomeState createState() => _HomeState();
}

class _HomeState extends State<Home> {
  String _infoText = "Informe os dados";
  GlobalKey<FormState> _formkey = GlobalKey();
  TextEditingController numero1Controller = TextEditingController();
  TextEditingController numero2Controller = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      //---------------------- BARRA DO APP ----------------------
      appBar: AppBar(
        //bara do app
        title: Text("Calculadora"), //titulo
        //centerTitle: true, //centralizar
        backgroundColor: Colors.cyan, //background (da barra)
        actions: <Widget>[
          //adicionar widgets (elementos)
          IconButton(
            //icone
            icon: Icon(Icons.refresh), //puxando um icone da "galeria"
            onPressed: () {
              //função ao pressionar
              resetFilds();
            },
          )
        ],
      ),
      backgroundColor: Colors.white, //background da PAGINA

      //--------------- CORPO DO APP -----------------------
      body: SingleChildScrollView(
        //para a pagina "descer em junto"
        padding: EdgeInsets.all(10.0),
        child: Form(
          //formulario
          key: _formkey,
          child: Column(
            //adicionando uma coluna
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: <Widget>[
              TextFormField(
                //campo de texto
                keyboardType: TextInputType.number,
                //tipo do campo de texto
                decoration: InputDecoration(
                  //decoração do campo
                  labelText: "Numero 1", //informação do campo
                  labelStyle: TextStyle(color: Colors.cyan), //style do campo
                ),
                style: TextStyle(color: Colors.cyan, fontSize: 20.0),
                //tamanho e cor da fonte
                controller: numero1Controller,
                //controller =  id que vai ser chamado na função

                validator: (value) {
                  //validação
                  if (value.isEmpty) {
                    //valida se o campo nao esta preenchido
                    return "Preencha este campo"; //se nao estiver aparece a mensagem
                  }
                },
              ),
              TextFormField(
                keyboardType: TextInputType.number,
                decoration: InputDecoration(
                  labelText: "Numero 2",
                  labelStyle: TextStyle(color: Colors.cyan),
                ),
                style: TextStyle(color: Colors.cyan, fontSize: 20.0),
                controller: numero2Controller,
                validator: (value) {
                  if (value.isEmpty) {
                    return "Preencha este campo";
                  }
                },
              ),
              ButtonTheme.bar(
                //permite configurar botoes em uma linha
                child: ButtonBar(
                  //linha de botoes
                  children: <Widget>[
                    RaisedButton(
                      //soma //um tipo de botao
                      onPressed: () {
                        //função de onclick
                        if (_formkey.currentState.validate()) {
                          //validação se foi preenchido
                          calculate("+"); //passa a função para qual vai ser mandada
                          FocusScope.of(context).requestFocus(new FocusNode());
                        }
                      },
                      color: Colors.cyan, //cor
                      child: Text(
                        //texto do botão
                        "+",
                        style: TextStyle(color: Colors.white, fontSize: 25.0),
                      ),
                    ),
                    RaisedButton(
                      //subtrai
                      onPressed: () {
                        if (_formkey.currentState.validate()) {
                          calculate("-");
                          FocusScope.of(context).requestFocus(new FocusNode());
                        }
                      },
                      color: Colors.cyan,
                      child: Text(
                        "-",
                        style: TextStyle(color: Colors.white, fontSize: 25.0),
                      ),
                    ),
                    RaisedButton(
                      //multiplica
                      onPressed: () {
                        if (_formkey.currentState.validate()) {
                          calculate("*");
                          FocusScope.of(context).requestFocus(new FocusNode());
                        }
                      },
                      color: Colors.cyan,
                      child: Text(
                        "X",
                        style: TextStyle(color: Colors.white, fontSize: 25.0),
                      ),
                    ),
                    RaisedButton(
                      //divide
                      onPressed: () {
                        if (_formkey.currentState.validate()) {
                          calculate("/");
                          FocusScope.of(context).requestFocus(new FocusNode());
                        }
                      },
                      color: Colors.cyan,
                      child: Text(
                        "/",
                        style: TextStyle(color: Colors.white, fontSize: 25.0),
                      ),
                    ),
                  ],
                  alignment: MainAxisAlignment.center,
                  mainAxisSize: MainAxisSize.min,
                ),
              ),
              Text(
                _infoText,
                style: TextStyle(color: Colors.cyan, fontSize: 25.0),
                textAlign: TextAlign.center,
              )
            ],
          ),
        ),
      ),
    );
  }

//------------------FUNÇÃO DE RESETAR------------------
  void resetFilds() {
    numero1Controller.text = "";
    numero2Controller.text = "";
    setState(() {
      _infoText = "Informe seus dados";
    });
  }

//------------------FUNÇÕES DAS OPERAÇÕES DOS BOTÕES------------------

  void calculate(String operacao){
    if (isNumeric(numero1Controller.text) && isNumeric(numero2Controller.text)){
      setState(() {
        double n1 = double.parse(numero1Controller.text);
        double n2 = double.parse(numero2Controller.text);

        double total = 0;
        if(operacao =="+"){
          total = n1 + n2;
        } else if(operacao =="-"){
          total = n1 - n2;
        } else if(operacao =="*"){
          total = n1 * n2;
        } else {
          total = n1 / n2;
        }

        _infoText = "Resultado: " + total.toStringAsPrecision(4);
      });
    } else{
      _showDialog('Aviso', 'Preencha somente numeros.'); // chamo a função do dialog com o "title" e a "message" mandados como parametros
    }
  }

  //----------------------- FUNÇÃO DO ALERTA ---------------------
  void _showDialog(String title, String message) { //defino a string e a ordem a ser passada como parametro quando chamo a função
    showDialog(
        context: context,
        builder: (BuildContext context) {
          //retorno um objeto do tipo Dialog
          return AlertDialog(
            title: new Text(title),
            content: new Text(message),
            actions: <Widget>[
              //defno os botoes na base do dialogo
              FlatButton(
                child: Text("Fechar"),
                onPressed: (){
                  Navigator.of(context).pop();
                },
              )
            ],
          );
        });
  }
}
