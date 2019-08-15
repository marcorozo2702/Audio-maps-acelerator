import 'package:flutter/material.dart';

void main() {
  runApp(
    MaterialApp(
      title: "Contador de pessoas",
      home: Home()
    )
  );
}

class Home extends StatefulWidget {
  @override
  _HomeState createState() => _HomeState();
}

class _HomeState extends State<Home> {

  //definindo as variaveis
  int _people = 0;
  String _infoText = "Pode entrar";

//função que incrementa o "numero" de pessoas
  void _changePeople(int delta){
  setState(() { //atualiza a tela a cada "click"
    if((_people<=9 && delta>0) || (_people>0 && delta < 0)){ //se o n° de pessoas <= 10 e delta > 0 ou n° > 0 e delta < 0 ele incrementa -->
      _people += delta; //pega ele mesmo (valor atualizado) +1 do delta recebido abaixo (abaixo no codigo)
    }
    if(_people<=9){
      _infoText="Pode entrar"; //variavel do testo recebe...
    } else{
      _infoText = "Lotado";
    }
  });

  }

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: <Widget>[
        Image.asset(
          "assets/bgRestaurante.jpg", //caminho da imagem
          fit: BoxFit.cover,
          height: 1000,
        ),
        Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'Pessoas: $_people',
              style: TextStyle(fontSize: 40.0, color: Colors.white),
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Padding(
                  padding: EdgeInsets.all(10.0), // padding para todos os lados
                  child: FlatButton(
                      onPressed: (){
                        _changePeople(1);
                      },
                      child: Text("+1", style: TextStyle(fontSize: 40.0, color: Colors.white),)
                  ),
                ),
                Padding(
                  padding: EdgeInsets.all(10.0), //padding para todos os lados
                  child: FlatButton(
                      onPressed: (){
                        _changePeople(-1);
                      },
                      child: Text("-1", style: TextStyle(fontSize: 40.0, color: Colors.white),)
                  ),
                ),

              ],
            ),
            Text(
              _infoText,
              style: TextStyle(fontSize: 40.0, color: Colors.white, fontStyle: FontStyle.italic),
            )
          ],
        )
      ],
    );
  }
}
