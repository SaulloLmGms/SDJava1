# sd-ufpb

Servidor: O servidor foi modificado para suportar múltiplos clientes usando um ThreadPool e uma lista de ClientHandler para gerenciar os clientes conectados. Foi adicionado um método para broadcast de mensagens para todos os clientes.
Cliente: O cliente foi modificado para permitir envio e recebimento de mensagens simultaneamente usando threads separadas para leitura e escrita.
ClientHandler: A classe ClientHandler foi modificada para incluir uma lista de todos os clientes conectados e um método para broadcast de mensagens para todos os clientes, exceto o remetente.
