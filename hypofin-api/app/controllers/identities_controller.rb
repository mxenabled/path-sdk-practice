# frozen_string_literal: true

require 'securerandom'

class IdentitiesController < ::ApplicationController
  def create
    render status: :ok, json: {
      token: ::SecureRandom.uuid,
      uid: "user.#{::Random.new.rand(1000)}"
    }
  end

  def destroy
    head status: :no_content
  end
end
