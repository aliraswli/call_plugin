class ContactModel {
  final String? id;
  final String? name;

  final List<String> phones;
  final List<String> photos;
  final List<String> emails;
  final List<String> websites;

  final String? nickname;
  final String? note;
  final String? birthday;
  final String? organization;
  final String? jobTitle;
  final String? address;

  const ContactModel({
    this.id,
    this.name,
    this.phones = const [],
    this.photos = const [],
    this.emails = const [],
    this.websites = const [],
    this.nickname,
    this.note,
    this.birthday,
    this.organization,
    this.jobTitle,
    this.address,
  });

  factory ContactModel.fromJson(Map<String, dynamic> json) {
    return ContactModel(
      id: json['id'] as String?,
      name: json['name'] as String?,
      phones: List<String>.from(json['phones'] ?? const []),
      photos: List<String>.from(json['photos'] ?? const []),
      emails: List<String>.from(json['emails'] ?? const []),
      websites: List<String>.from(json['websites'] ?? const []),
      nickname: json['nickname'] as String?,
      note: json['note'] as String?,
      birthday: json['birthday'] as String?,
      organization: json['organization'] as String?,
      jobTitle: json['jobTitle'] as String?,
      address: json['address'] as String?,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'phones': phones,
      'photos': photos,
      'emails': emails,
      'websites': websites,
      'nickname': nickname,
      'note': note,
      'birthday': birthday,
      'organization': organization,
      'jobTitle': jobTitle,
      'address': address,
    };
  }
}
