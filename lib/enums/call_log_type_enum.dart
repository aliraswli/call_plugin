enum CallLogTypeEnum {
  none(0),
  incoming(1),
  outgoing(2),
  missed(3),
  voicemail(4),
  rejected(5),
  blocked(6);

  final num type;

  factory CallLogTypeEnum.fromValue(int type) {
    return CallLogTypeEnum.values.firstWhere(
      (element) => element.type == type,
      orElse: () => CallLogTypeEnum.none,
    );
  }

  const CallLogTypeEnum(this.type);
}
