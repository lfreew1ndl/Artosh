export class RoomModel {
  constructor(public sessionId: string, public userLogin: string, public ipAddress: string, public room: string, public time: string) {
  }
}
