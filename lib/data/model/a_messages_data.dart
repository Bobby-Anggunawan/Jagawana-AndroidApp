class AMessagesData{
  final String messages;
  final MessageSender sender;
  final DateTime time;

  const AMessagesData(this.messages, this.sender, this.time);
}

enum MessageSender{Me, Other}