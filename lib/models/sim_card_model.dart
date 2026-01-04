class SimCardModel {
  const SimCardModel({
    this.id,
    this.slot,
    this.subscriptionId,
    this.carrierName,
    this.countryIso,
    this.displayName,
  });

  factory SimCardModel.fromJson(dynamic json) {
    return SimCardModel(
      id: json['id'],
      slot: json['slot'],
      subscriptionId: json['subscriptionId'],
      carrierName: json['carrierName'],
      countryIso: json['countryIso'],
      displayName: json['displayName'],
    );
  }

  static List<SimCardModel> fromList(dynamic json) {
    return (json as List).map(SimCardModel.fromJson).toList();
  }

  final String? id;
  final num? slot;
  final num? subscriptionId;
  final String? carrierName;
  final String? countryIso;
  final String? displayName;

  SimCardModel copyWith({
    String? id,
    num? slot,
    num? subscriptionId,
    String? carrierName,
    String? countryIso,
    String? displayName,
  }) => SimCardModel(
    id: id ?? this.id,
    slot: slot ?? this.slot,
    subscriptionId: subscriptionId ?? this.subscriptionId,
    carrierName: carrierName ?? this.carrierName,
    countryIso: countryIso ?? this.countryIso,
    displayName: displayName ?? this.displayName,
  );

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['id'] = id;
    map['slot'] = slot;
    map['subscriptionId'] = subscriptionId;
    map['carrierName'] = carrierName;
    map['countryIso'] = countryIso;
    map['displayName'] = displayName;
    return map;
  }
}
