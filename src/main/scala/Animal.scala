trait Animal{
  val name: String
}

@TalkingAnimalSpell
case class Dog(name: String) extends Animal